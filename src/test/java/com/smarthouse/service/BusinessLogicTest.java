package com.smarthouse.service;

import com.smarthouse.dao.IDao;
import com.smarthouse.dao.entity.*;
import com.smarthouse.dao.implementation.*;
import org.junit.*;
import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

import static com.smarthouse.service.libs.enums.EnumProductSort.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class BusinessLogicTest {

    private IDao<Category, Integer> cdao;
    private IDao<ProductCard, String> pdao;
    private IDao<AttributeName, String> adao;
    private IDao<OrderMain, Integer> odao;
    private IDao< Customer, String> custdao;
    private BusinessLogic bl;

    @Before
    public void init() throws SQLException {
        bl = new BusinessLogic();
        cdao = new CategoryDaoImpl();
        pdao = new ProductCardDaoImpl();
        adao = new AttributeNameDaoImpl();
        odao = new OrderMainDaoImpl();
        custdao = new CustomerDaoImpl();
    }

    @After
    public void after() throws SQLException {
        bl = null;
        cdao = null;
    }

    @Test
    public void getRootCategory() throws Exception {
        List<Category> list = bl.getRootCategory();
        list.forEach(a -> System.out.println(a.getName()));
    }

    @Test
    public void getSubCategories() throws Exception {
        List<Category> list = bl.getSubCategories(cdao.get(112));
        list.forEach(a -> {
            assertThat(a.getCategory().getName(), is(isOneOf("catname1", "catname11", "catname12", "catname13")));
            assertThat(a.getCategory().getName(), is(notNullValue()));
            assertThat(a.getCategory().getName(), isA(String.class));
            assertThat(a.getCategory(), isA(Category.class));
            assertThat(a.getCategory().getId(), isA(Integer.class));
        });
    }

    @Test
    public void getProductCardsByCategory() throws Exception {
        List<ProductCard> list = bl.getProductCardsByCategory(cdao.get(1));

        list.forEach(a -> {
            assertThat(a.getName(), is(isOneOf("name")));
            assertThat(a.getName(), is(notNullValue()));
            assertThat(a.getName(), isA(String.class));
            assertThat(a, isA(ProductCard.class));
            assertThat(a.getSku(), isA(String.class));
        });

    }

    @Test
    public void getVisualListByProduct() throws Exception {
        List<Visualization> list = bl.getVisualListByProduct(pdao.get("333"));

        list.forEach(a -> {
            assertThat(a.getUrl(), is(isOneOf("sddsad", "s2", "s3")));
            assertThat(a.getUrl(), is(notNullValue()));
            assertThat(a.getUrl(), isA(String.class));
            assertThat(a, isA(Visualization.class));
        });
    }

    @Test
    public void getAttrValuesByProduct() throws Exception {
        List<AttributeValue> list = bl.getAttrValuesByProduct(pdao.get("111"));

        list.forEach(a -> {
            assertThat(a.getValue(), is(isOneOf("red", "green", "blue", "big", "small")));
            assertThat(a.getValue(), is(notNullValue()));
            assertThat(a.getValue(), isA(String.class));
            assertThat(a, isA(AttributeValue.class));
        });
    }

    @Test
    public void getAttrValuesByName() throws Exception {
        List<AttributeValue> list = bl.getAttrValuesByName(adao.get("width"));

        list.forEach(a -> {
            assertThat(a.getValue(), is(isOneOf("big", "small")));
            assertThat(a.getValue(), is(notNullValue()));
            assertThat(a.getValue(), isA(String.class));
            assertThat(a, isA(AttributeValue.class));
            assertThat(a.getValue(), isA(String.class));
        });
    }

    @Test
    public void getItemOrdersByProdCard() throws Exception {
        List<OrderItem> list = bl.getItemOrdersByProdCard(pdao.get("qwe123"));

        list.forEach(a -> {
            assertThat(a.getTotalprice(), is(isOneOf(555, 444, 666,777)));
            assertThat(a.getTotalprice(), is(notNullValue()));
            assertThat(a.getTotalprice(), isA(Integer.class));
            assertThat(a, isA(OrderItem.class));
            assertThat(a.getAmount(), isA(Integer.class));
        });
    }

    @Test
    public void getItemOrdersByOrderMain() throws Exception {
        OrderMain orderMain = new OrderMain("addr", 2, custdao.get("vbn@bk.ru"));
        odao.add(orderMain);
        List<OrderItem> list = bl.getItemOrdersByOrderMain(odao.get(1));

        list.forEach(a -> {
            assertThat(a.getAmount(), is(isOneOf(5, 4)));
            assertThat(a.getAmount(), is(notNullValue()));
            assertThat(a.getAmount(), isA(Integer.class));
            assertThat(a, isA(OrderItem.class));
            assertThat(a.getTotalprice(), isA(Integer.class));
        });
    }

    @Test
    public void getOrdersByCustomer() throws Exception {
        List<OrderMain> list = bl.getOrdersByCustomer(custdao.get("vbn@bk.ru"));

        list.forEach(a -> {
            assertThat(a.getAddress(), is(isOneOf("addr")));
            assertThat(a.getAddress(), is(notNullValue()));
            assertThat(a.getAddress(), isA(String.class));
            assertThat(a, isA(OrderMain.class));
            assertThat(a.getCustomer(), isA(Customer.class));
        });
    }

    @Test
    public void sortByPopularity() throws Exception {
        List<ProductCard> productCards = pdao.getAll();
        productCards.forEach(a -> System.out.print(a.getLikes() + " "));
        System.out.println();
        List<ProductCard> productCardsSorted = bl.productSort(productCards, SORT_BY_POPULARITY);
        productCardsSorted.forEach(a -> System.out.print(a.getLikes() + "  "));
    }

    @Test
    public void sortByUnpopularity() throws Exception {
        List<ProductCard> productCards = pdao.getAll();
        productCards.forEach(a -> System.out.print(a.getDislikes() + " "));
        System.out.println();
        List<ProductCard> productCardsSorted = bl.productSort(productCards, SORT_BY_UNPOPULARITY);
        productCardsSorted.forEach(a -> System.out.print(a.getDislikes() + "  "));
    }

    @Test
    public void sortByName() throws Exception {
        List<ProductCard> productCards = pdao.getAll();
        productCards.forEach(a -> System.out.print(a.getName() + " "));
        List<ProductCard> productCardsSorted = bl.productSort(productCards, SORT_BY_NAME);
        System.out.println(productCards.size());
        System.out.println(productCardsSorted.size());
        productCardsSorted.forEach(a -> System.out.print(a.getName() + "  "));
    }

    @Test
    public void sortByNameReversed() throws Exception {
        List<ProductCard> productCards = pdao.getAll();
        productCards.forEach(a -> System.out.print(a.getName() + " "));
        System.out.println();
        List<ProductCard> productCardsSorted = bl.productSort(productCards, SORT_BY_NAME_REVERSED);
        productCardsSorted.forEach(a -> System.out.print(a.getName() + "  "));
    }

    @Test
    public void sortByLowerPrice() throws Exception {
        List<ProductCard> productCards = pdao.getAll();
        productCards.forEach(a -> System.out.print(a.getPrice() + " "));
        System.out.println();
        List<ProductCard> productCardsSorted = bl.productSort(productCards, SORT_BY_LOW_PRICE);
        productCardsSorted.forEach(a -> System.out.print(a.getPrice() + "  "));
    }

    @Test
    public void sortByHigherPrice() throws Exception {
        List<ProductCard> productCards = pdao.getAll();
        productCards.forEach(a -> System.out.print(a.getPrice() + " "));
        System.out.println();
        List<ProductCard> productCardsSorted = bl.productSort(productCards, SORT_BY_HIGH_PRICE);
        productCardsSorted.forEach(a -> System.out.print(a.getPrice() + "  "));
    }

    @Test
    public void sortByAmount() throws Exception {
        List<ProductCard> productCards = pdao.getAll();
        productCards.forEach(a -> System.out.print(a.getAmount() + " "));
        System.out.println();
        List<ProductCard> productCardsSorted = bl.productSort(productCards, SORT_BY_AMOUNT);
        productCardsSorted.forEach(a -> System.out.print(a.getAmount() + "  "));
    }

    @Test
    public void isProductAvailable() throws Exception {
        assertTrue(bl.isProductAvailable("222"));
    }

    @Test(expected = NoResultException.class)
    public void isProductAvailableThrownIfNotBelong(){
        assertTrue(bl.isProductAvailable("555"));
    }

    @Test
    public void completeOrder() throws Exception {
        bl.completeOrder("kya@bk.ru");
    }
}