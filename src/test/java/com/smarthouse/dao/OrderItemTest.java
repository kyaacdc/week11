package com.smarthouse.dao;

import com.smarthouse.dao.entity.*;
import com.smarthouse.dao.implementation.OrderItemDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class OrderItemTest {

    private IDao<OrderItem, Integer> service;

    @Before
    public void init() {
        service = new OrderItemDaoImpl();
    }

    @After
    public void after() {
        service = null;
    }

    @Test
    public void testSaveRecord() {
        Category category = new Category("CatNewName", "Cat Descrip", null);
        ProductCard productCard = new ProductCard("qwe123", "OrderedProduct", 123, 1000, 23, 12, "prodDesc", category);
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain = new OrderMain("OrderAddress", 1, customer);

        OrderItem orderItem1 = new OrderItem(5,555, productCard, orderMain);

        //Record to DB
        OrderItem orderItem = service.add(orderItem1);

        assertThat(orderItem.getAmount(), is(equalTo(5)));
        assertThat(orderItem.getTotalprice(), is(equalTo(555)));
        assertThat(orderItem.getAmount(), isA(Integer.class));
        assertThat(orderItem, is(notNullValue()));
        assertThat(orderItem, is(anything()));
        assertThat(orderItem.getTotalprice(), is(greaterThan(554)));
        assertThat(orderItem.getAmount(), is(not(lessThan(4))));
        assertThat(orderItem.getClass(), is(typeCompatibleWith(OrderItem.class)));
    }

    @Test
    public void testDeleteRecord() {
       Category category = new Category("CatNewName", "Cat Descrip", null);
        ProductCard productCard = new ProductCard("qwe123", "OrderedProduct", 123, 1000, 23, 12, "prodDesc", category);
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain = new OrderMain("OrderAddress", 1, customer);

        OrderItem orderItem1 = new OrderItem(5,555, productCard, orderMain);

        //Record to DB
        OrderItem orderItem = service.add(orderItem1);

        //Delete from DB
        service.delete(orderItem.getId());
    }

    @Test
    public void testSelect() {
         Category category = new Category("CatNewName", "Cat Descrip", null);
        ProductCard productCard = new ProductCard("qwe123", "OrderedProduct", 123, 1000, 23, 12, "prodDesc", category);
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain = new OrderMain("OrderAddress", 1, customer);

        OrderItem orderItem1 = new OrderItem(5,555, productCard, orderMain);

        //Record to DB
        OrderItem orderItem = service.add(orderItem1);

        //Get from DB
        OrderItem orderItemFromDB = service.get(orderItem.getId());

        assertThat(orderItemFromDB.getTotalprice(), is(equalTo(555)));
        assertThat(orderItemFromDB.getProductCard().getCategory().getName(), is(equalTo("CatNewName")));
        assertThat(orderItemFromDB.getTotalprice(), isA(Integer.class));
        assertThat(orderItemFromDB, is(notNullValue()));
        assertThat(orderItemFromDB, is(anything()));
        assertThat(orderItemFromDB.getClass(), is(typeCompatibleWith(OrderItem.class)));
    }

    @Test
    public void testUpdate() {
        Category category = new Category("CatNewName", "Cat Descrip", null);
        ProductCard productCard = new ProductCard("qwe123", "OrderedProduct", 123, 1000, 23, 12, "prodDesc", category);
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain = new OrderMain("OrderAddress", 1, customer);

        OrderItem orderItem1 = new OrderItem(5,555, productCard, orderMain);

        //Record to DB
        OrderItem orderItem = service.add(orderItem1);


        assertThat(orderItem.getAmount(), is(equalTo(5)));
        assertThat(orderItem.getTotalprice(), is(equalTo(555)));
        assertThat(orderItem.getAmount(), isA(Integer.class));
        assertThat(orderItem, is(notNullValue()));
        assertThat(orderItem, is(anything()));
        assertThat(orderItem.getTotalprice(), is(greaterThan(554)));
        assertThat(orderItem.getAmount(), is(not(lessThan(4))));
        assertThat(orderItem.getClass(), is(typeCompatibleWith(OrderItem.class)));

        orderItem.setAmount(6);

        //Update
        service.update(orderItem);

        //Get update info
        OrderItem orderItem2 = service.add(orderItem);

        assertThat(orderItem.getAmount(), is(equalTo(6)));
    }

    @Test
    public void testGetAll(){
        Category category = new Category("CatNewName", "Cat Descrip", null);
        ProductCard productCard = new ProductCard("qwe123", "OrderedProduct", 123, 1000, 23, 12, "prodDesc", category);
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain = new OrderMain("OrderAddress", 1, customer);

        OrderItem orderItem1 = new OrderItem(5,555, productCard, orderMain);
        OrderItem orderItem2 = new OrderItem(6,666, productCard, orderMain);
        OrderItem orderItem3 = new OrderItem(7,777, productCard, orderMain);


        //Record to DB
        service.add(orderItem1);
        service.add(orderItem2);
        service.add(orderItem3);


        List<OrderItem> orderItems = service.getAll();

        assertThat(orderItems, is(notNullValue()));
        assertThat(orderItems, is(anything()));
        assertThat(orderItems.get(0), isA(OrderItem.class));
    }
}


