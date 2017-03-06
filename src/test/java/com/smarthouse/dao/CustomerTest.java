package com.smarthouse.dao;

import com.smarthouse.dao.entity.Customer;
import com.smarthouse.dao.implementation.CustomerDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CustomerTest {

    private IDao<Customer, String> service;

    @Before
    public void init(){
        service = new CustomerDaoImpl();
    }

    @After
    public void after(){
        service = null;
    }

    @Test
    public void testSaveRecord(){
        Customer customer1 = new Customer("kya@bk.ru", "customerName", false,  "0503337178");

        //Record to DB
        Customer customer = service.add(customer1);

        assertThat(customer.getEmail(), is(equalTo("kya@bk.ru")));
        assertThat(customer.getName(), is(equalTo("customerName")));
        assertThat(customer.isSubscribe(), is(not(true)));
        assertThat(customer.getPhone(), is(equalTo("0503337178")));
        assertThat(customer.getName(), isA(String.class));
        assertThat(customer, is(notNullValue()));
        assertThat(customer, is(anything()));
        assertThat(customer.getName(), is(greaterThan("")));
        assertThat(customer.getName(), is(not(lessThan(""))));
        assertThat(customer.getClass(), is(typeCompatibleWith(Customer.class)));
    }

    @Test
    public void testDeleteRecord(){
        Customer customer1 = new Customer("anniya@bk.ru", "2customerName", false,  "087696966");

        //Record to DB
        Customer customer = service.add(customer1);


        //Delete from DB
        service.delete(customer.getEmail());
    }

    @Test
    public void testSelect(){
        Customer customer1 = new Customer("anniya@bk.ru", "2customerName", false,  "087696966");

        //Record to DB
        Customer customer = service.add(customer1);

        //Get from DB
        Customer customerFromDb = service.get(customer.getEmail());

        assertThat(customerFromDb.getEmail(), is(equalTo("anniya@bk.ru")));
        assertThat(customerFromDb.getName(), is(equalTo("2customerName")));
        assertThat(customerFromDb.isSubscribe(), is(not(true)));
        assertThat(customerFromDb.getPhone(), is(equalTo("087696966")));
        assertThat(customerFromDb.getName(), isA(String.class));
        assertThat(customerFromDb, is(notNullValue()));
        assertThat(customerFromDb, is(anything()));
        assertThat(customerFromDb.getName(), is(greaterThan("")));
        assertThat(customerFromDb.getName(), is(not(lessThan(""))));
        assertThat(customerFromDb.getClass(), is(typeCompatibleWith(Customer.class)));
    }

    @Test
    public void testUpdate(){
        Customer customer1 = new Customer("anniya@bk.ru", "2customerName", false,  "087696966");

        //Record to DB
        Customer customer = service.add(customer1);

        assertThat(customer.getEmail(), is(equalTo("anniya@bk.ru")));
        assertThat(customer.getName(), is(equalTo("2customerName")));
        assertThat(customer.isSubscribe(), is(not(true)));
        assertThat(customer.getPhone(), is(equalTo("087696966")));
        assertThat(customer.getName(), isA(String.class));
        assertThat(customer, is(notNullValue()));
        assertThat(customer, is(anything()));
        assertThat(customer.getName(), is(greaterThan("")));
        assertThat(customer.getName(), is(not(lessThan(""))));
        assertThat(customer.getClass(), is(typeCompatibleWith(Customer.class)));

        customer1.setName("4cust");

        //Update
        service.update(customer1);

        //Get update info
        Customer customer2 = service.get(customer1.getEmail());

        assertThat(customer2.getName(), is(equalTo("4cust")));
        assertThat(customer2.getName(), isA(String.class));
        assertThat(customer2, is(notNullValue()));
        assertThat(customer2, is(anything()));
        assertThat(customer2.getName(), is(greaterThan("")));
        assertThat(customer2.getName(), is(not(lessThan(""))));
        assertThat(customer2.getClass(), is(typeCompatibleWith(Customer.class)));
    }

    @Test
    public void testGetAll(){
        Customer customer1 = new Customer("anniya@bk.ru", "2customerName", false,  "087696966");
        Customer customer2 = new Customer("rrre@bk.ru", "3customerName", false,  "678889868");
        Customer customer3 = new Customer("vbn@bk.ru", "22customerName", false,  "698698");

        service.add(customer1);
        service.add(customer2);
        service.add(customer3);

        List<Customer> attributeNames = service.getAll();

        assertThat(attributeNames, is(notNullValue()));
        assertThat(attributeNames, is(anything()));
        assertThat(attributeNames.get(0), isA(Customer.class));
    }
}

