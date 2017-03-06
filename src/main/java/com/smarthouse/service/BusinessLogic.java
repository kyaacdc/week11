package com.smarthouse.service;

import com.smarthouse.dao.IDao;
import com.smarthouse.dao.entity.*;
import com.smarthouse.dao.implementation.*;
import com.smarthouse.service.libs.enums.EnumSearch;
import com.smarthouse.service.libs.enums.EnumProductSort;
import com.smarthouse.service.libs.validators.EmailValidator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.smarthouse.dao.PersistenceManager.ENT;
import static com.smarthouse.service.libs.enums.EnumSearch.FIND_ALL;

public class BusinessLogic {

    private EntityManager em;

    public BusinessLogic(){
        em = ENT.getEntityManager();
    }

    /**
     *   Method makeOrder is add or update new Customer into database
     *   and also add order info in DB with compute total price
     *  @param email user email address for identy each user by primary key
     *  @param name name of user (optional)
     *  @param phone phone number of user (optional)
     *  @param address address for receive order
     *  @param amount amount of products in order
     *  @param sku unique id of each product
     */
    public void makeOrder(String email, String name, String phone,
                          String address, int amount, String sku){

        EmailValidator emailValidator = new EmailValidator();

        if (!emailValidator.validate(email))
            throw new NoResultException();

        IDao<ProductCard, String> pdao = new ProductCardDaoImpl();
        IDao<Customer, String> custdao = new CustomerDaoImpl();
        IDao<OrderMain, Integer> omdao = new OrderMainDaoImpl();
        IDao<OrderItem, Integer> oidao = new OrderItemDaoImpl();

        ProductCard productCard = pdao.get(sku);
        int totalPrice = productCard.getPrice() * amount;

        Customer customer = new Customer(email, name, true, phone);
        OrderMain orderMain = new OrderMain(address, 1, customer);
        OrderItem orderItem = new OrderItem(amount, totalPrice, productCard, orderMain);

        custdao.add(customer);
        omdao.add(orderMain);
        oidao.add(orderItem);
    }

    /**
     *   Method completeOrder need for update amount of ProductCard
     *   on warehouse and update status of order in OrderMain in table
     *  @param email is  a user email for making changes
     *  @return void type
     *  @throws NoResultException if amount of products in our order
     *  less than on warehouse
     */
    public void completeOrder(String email){
        if(validateOrder(email)) {
            IDao<Customer, String> custdao = new CustomerDaoImpl();
            IDao<ProductCard, String> pdao = new ProductCardDaoImpl();
            IDao<OrderMain, Integer> omdao = new OrderMainDaoImpl();
            Customer customer = custdao.get(email);
            List<OrderMain> ordersByCustomer = getOrdersByCustomer(customer);

            for (OrderMain om : ordersByCustomer) {
                List<OrderItem> itemOrdersByOrderMain = getItemOrdersByOrderMain(om);
                for (OrderItem oi : itemOrdersByOrderMain) {
                    int newAmount = oi.getProductCard().getAmount() - oi.getAmount();
                    ProductCard productCard = oi.getProductCard();
                    productCard.setAmount(newAmount);
                    pdao.update(productCard);
                }
                om.setStatus(2);
                omdao.update(om);
            }
        }
        else
            throw new NoResultException("This amount of products not exist on our warehouse");
    }

    /**
     *   Method validateOrder need for check amount of ProductCard
     *   on warehouse.
     *  @param email is  a user email for making changes
     *  @return boolean type. True if amount in order >= amount on
     *  warehouse
     */
    public boolean validateOrder(String email){
        boolean isExist = true;
        IDao<Customer, String> custdao = new CustomerDaoImpl();
        Customer customer = custdao.get(email);
        List<OrderMain> ordersByCustomer = getOrdersByCustomer(customer);
        l1:
        for(OrderMain om: ordersByCustomer){
            List<OrderItem> itemOrdersByOrderMain = getItemOrdersByOrderMain(om);
            for (OrderItem oi : itemOrdersByOrderMain) {
                if(!isProductAvailable(oi.getProductCard().getSku())){
                    isExist = false;
                    break l1;
                }
            }
        }
        return isExist;
    }

    //Return product availabitity in storehouse
    public boolean isProductAvailable(String sku) {
        IDao<ProductCard, String> prodIDao;
        prodIDao = new ProductCardDaoImpl();
        if(prodIDao.get(sku) == null)
            throw new NoResultException("This product does not belong to our warehouse");
        return prodIDao.get(sku).getAmount() > 0;
    }

    /**
     *   Method productSort(List<ProductCard> list, EnumProductSort criteria)
     *  for sort items list by different custom parameters
     *  @param criteria function to apply sort criteria, like one of:
     *                  SORT_BY_NAME
     *                  SORT_BY_REVERSED
     *                  SORT_BY_AMOUNT
     *                  SORT_BY_LIKES (_DISLIKES)
     *                  SORT_BY_PRICE (_REVERSED)
     *  @return sorted List<ProductCard>
     *  This is my old release sortByName method without enum class,
     *  but to many copy/paste code was,w ith 8 sorting methods, now only 1
     *  public List<ProductCard> sortByNameReversed(List<ProductCard> list){
     *     return list.stream()
     *             .sorted(Comparator.comparing(ProductCard::getName).reversed())
     *              .collect(Collectors.toList());
     *  }
     */
    public List<ProductCard> productSort(List<ProductCard> list, EnumProductSort criteria){
        return list.stream()
                .sorted(criteria)
                .collect(Collectors.toList());
    }

    /**
     *   Method findProduct need for find any ProductCard
     *   on warehouse by String criteria in all Db     *
     *  @param criteria  String is  a string for the find
     *  @return Set<ProductCard> type with found set of products
     */
    public Set<ProductCard> findAllProducts(String criteria){
        return FIND_ALL.findAll(criteria);
    }

    /**
     *   Method findProduct need for find any ProductCard
     *   on warehouse by String criteria, in custom place.
     *  @param criteria  String is  a string for the find
     *  @param placeForFind enumeration for choose sort criteria:
     *                      FIND_ALL,
     *                      FIND_BY_NAME,
     *                      FIND_IN_PROD_DESC,
     *                      FIND_IN_CATEGORY_NAME,
     *                      FIND_IN_CATEGORY_DESC;
     *  @return Set<ProductCard> found results of products
     */
    public Set<ProductCard> findProducts(String criteria, EnumSearch placeForFind){
        Set<ProductCard> result;
        IDao<ProductCard, String> pdao = new ProductCardDaoImpl();
        List<ProductCard> all = pdao.getAll();
        result = placeForFind.findByPlace(criteria, all, placeForFind);
        return result;
    }


    // Methods for getting lists of various items

    public List<Category> getRootCategory(){
        IDao<Category,Integer> categoryIDao = new CategoryDaoImpl();
        return categoryIDao.getAll().stream()
                .filter(a -> a.getCategory() == null)
                .collect(Collectors.toList());
    }

    public List<Category> getSubCategories(Category category){
        TypedQuery<Category> typedQuery = em
                .createQuery("SELECT c FROM Category c WHERE c.category.id = ?", Category.class);
        typedQuery.setParameter(0, category.getId());
        return typedQuery.getResultList();
    }

    public List<ProductCard> getProductCardsByCategory(Category category){
        TypedQuery<ProductCard> typedQuery = em
                .createQuery("SELECT p FROM ProductCard p WHERE p.category.id = ?", ProductCard.class);
        typedQuery.setParameter(0, category.getId());
        return typedQuery.getResultList();
    }

    public List<Visualization> getVisualListByProduct(ProductCard productCard){
        TypedQuery<Visualization> typedQuery = em
                .createQuery("SELECT v FROM Visualization v WHERE v.productCard.sku = ?", Visualization.class);
        typedQuery.setParameter(0, productCard.getSku());
        return typedQuery.getResultList();
    }

    public List<AttributeValue> getAttrValuesByProduct(ProductCard productCard){
        TypedQuery<AttributeValue> typedQuery = em
                .createQuery("SELECT a FROM AttributeValue a WHERE a.productCard.sku = ?", AttributeValue.class);
        typedQuery.setParameter(0, productCard.getSku());
        return typedQuery.getResultList();
    }

    public List<AttributeValue> getAttrValuesByName(AttributeName attributeName){
        TypedQuery<AttributeValue> typedQuery = em
                .createQuery("SELECT a FROM AttributeValue a WHERE a.attributeName.name = ?", AttributeValue.class);
        typedQuery.setParameter(0, attributeName.getName());
        return typedQuery.getResultList();
    }

    public List<OrderItem> getItemOrdersByProdCard (ProductCard productCard){
        TypedQuery<OrderItem> typedQuery = em
                .createQuery("SELECT i FROM OrderItem i WHERE i.productCard.sku = ?", OrderItem.class);
        typedQuery.setParameter(0, productCard.getSku());
        return typedQuery.getResultList();
    }

    public List<OrderItem> getItemOrdersByOrderMain (OrderMain orderMain){
        TypedQuery<OrderItem> typedQuery = em
                .createQuery("SELECT i FROM OrderItem i WHERE i.orderMain.orderid = ? AND i.orderMain.status = 1", OrderItem.class);
        typedQuery.setParameter(0, orderMain.getOrderid());
        return typedQuery.getResultList();
    }

    public List<OrderMain> getOrdersByCustomer (Customer customer){
        TypedQuery<OrderMain> typedQuery = em
                .createQuery("SELECT o FROM OrderMain o WHERE o.customer.email = ?", OrderMain.class);
        typedQuery.setParameter(0, customer.getEmail());
        return typedQuery.getResultList();
    }
}
