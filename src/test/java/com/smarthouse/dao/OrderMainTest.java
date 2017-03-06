package com.smarthouse.dao;

import com.smarthouse.dao.entity.Customer;
import com.smarthouse.dao.entity.OrderMain;
import com.smarthouse.dao.implementation.OrderMainDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class OrderMainTest {

    private IDao<OrderMain, Integer> service;

    @Before
    public void init() {
        service = new OrderMainDaoImpl();
    }

    @After
    public void after() {
        service = null;
    }

    @Test
    public void testSaveRecord() {
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain1 = new OrderMain("OrderAddress", 1, customer);

        //Record to DB
        OrderMain orderMain = service.add(orderMain1);

        assertThat(orderMain.getAddress(), is(equalTo("OrderAddress")));
        assertThat(orderMain.getCustomer().getEmail(), is(equalTo("kya@bk.ru")));
        assertThat(orderMain.getAddress(), isA(String.class));
        assertThat(orderMain, is(notNullValue()));
        assertThat(orderMain, is(anything()));
        assertThat(orderMain.getAddress(), is(greaterThan("")));
        assertThat(orderMain.getAddress(), is(not(lessThan(""))));
        assertThat(orderMain.getClass(), is(typeCompatibleWith(OrderMain.class)));
    }

    @Test
    public void testDeleteRecord() {
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain1 = new OrderMain("OrderAddress", 1, customer);

        //Record to DB
        OrderMain orderMain = service.add(orderMain1);

        //Delete from DB
        service.delete(orderMain.getOrderid());
    }

    @Test
    public void testSelect() {
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain1 = new OrderMain("OrderAddress", 1, customer);

        //Record to DB
        OrderMain orderMain = service.add(orderMain1);

        //Get from DB
        OrderMain orderMainFromDB = service.get(orderMain.getOrderid());

        assertThat(orderMainFromDB.getAddress(), is(equalTo("OrderAddress")));
        assertThat(orderMainFromDB.getCustomer().getEmail(), is(equalTo("kya@bk.ru")));
        assertThat(orderMainFromDB.getAddress(), isA(String.class));
        assertThat(orderMainFromDB, is(notNullValue()));
        assertThat(orderMainFromDB, is(anything()));
        assertThat(orderMainFromDB.getAddress(), is(greaterThan("")));
        assertThat(orderMainFromDB.getAddress(), is(not(lessThan(""))));
        assertThat(orderMainFromDB.getClass(), is(typeCompatibleWith(OrderMain.class)));
    }

    @Test
    public void testUpdate() {
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");
        OrderMain orderMain1 = new OrderMain("OrderAddress", 1, customer);

        //Record to DB
        OrderMain orderMain = service.add(orderMain1);

        assertThat(orderMain.getAddress(), is(equalTo("OrderAddress")));
        assertThat(orderMain.getCustomer().getEmail(), is(equalTo("kya@bk.ru")));
        assertThat(orderMain.getAddress(), isA(String.class));
        assertThat(orderMain, is(notNullValue()));
        assertThat(orderMain, is(anything()));
        assertThat(orderMain.getAddress(), is(greaterThan("")));
        assertThat(orderMain.getAddress(), is(not(lessThan(""))));
        assertThat(orderMain.getClass(), is(typeCompatibleWith(OrderMain.class)));

        orderMain1.setAddress("newAddress");

        //Update
        service.update(orderMain1);

        //Get update info
        OrderMain orderMain2 = service.add(orderMain1);

        assertThat(orderMain2.getAddress(), is(equalTo("newAddress")));
        assertThat(orderMain2.getCustomer().getEmail(), is(equalTo("kya@bk.ru")));
        assertThat(orderMain2.getAddress(), isA(String.class));
        assertThat(orderMain2, is(notNullValue()));
        assertThat(orderMain2, is(anything()));
        assertThat(orderMain2.getAddress(), is(greaterThan("")));
        assertThat(orderMain2.getAddress(), is(not(lessThan(""))));
        assertThat(orderMain2.getClass(), is(typeCompatibleWith(OrderMain.class)));
    }

    @Test
    public void testGetAll(){
        Customer customer = new Customer("kya@bk.ru", "Yuriy", false, "7585885");

        OrderMain orderMain1 = new OrderMain("1OrderAddress", 1, customer);
        OrderMain orderMain2 = new OrderMain("2OrderAddress", 1, customer);
        OrderMain orderMain3 = new OrderMain("3OrderAddress", 1, customer);

        //Record to DB
        service.add(orderMain1);
        service.add(orderMain2);
        service.add(orderMain3);


        List<OrderMain> ordersMain = service.getAll();

        assertThat(ordersMain, is(notNullValue()));
        assertThat(ordersMain, is(anything()));
        assertThat(ordersMain.get(0), isA(OrderMain.class));
    }
}


