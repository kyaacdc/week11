package com.smarthouse.dao;

import com.smarthouse.dao.implementation.AttributeNameDaoImpl;
import com.smarthouse.dao.entity.AttributeName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class AttributeNameTest {

    private IDao<AttributeName, String> service;

    @Before
    public void init(){
        service = new AttributeNameDaoImpl();
    }

    @After
    public void after(){
        service = null;
    }

    @Test
    public void testSaveRecord(){
        AttributeName attributeName1 = new AttributeName();
        attributeName1.setName("^&$$^&$^fgfhf65675");

        //Record to DB
        AttributeName attributeName = service.add(attributeName1);

        assertThat(attributeName.getName(), is(equalTo("color")));
        assertThat(attributeName.getName(), isA(String.class));
        assertThat(attributeName, is(notNullValue()));
        assertThat(attributeName, is(anything()));
        assertThat(attributeName.getName(), is(greaterThan("")));
        assertThat(attributeName.getName(), is(not(lessThan(""))));
        assertThat(attributeName.getClass(), is(typeCompatibleWith(AttributeName.class)));
    }

    @Test
    public void testDeleteRecord(){
        //Create attribute
        AttributeName attributeName1 = new AttributeName();
        attributeName1.setName("weight");

        //Record to DB
        AttributeName attributeName = service.add(attributeName1);

        //Delete from DB
        service.delete(attributeName.getName());
    }

    @Test
    public void testSelect(){
        //Create attribute
        AttributeName attributeName1 = new AttributeName();
        attributeName1.setName("width");

        //Record to DB
        AttributeName attributeName = service.add(attributeName1);

        //Get from DB
        AttributeName cityFromDB = service.get(attributeName.getName());

        assertThat(attributeName.getName(), is(equalTo("width")));
        assertThat(attributeName.getName(), isA(String.class));
        assertThat(attributeName, is(notNullValue()));
        assertThat(attributeName, is(anything()));
        assertThat(attributeName.getName(), is(greaterThan("")));
        assertThat(attributeName.getName(), is(not(lessThan(""))));
        assertThat(attributeName.getClass(), is(typeCompatibleWith(AttributeName.class)));
    }

    @Test
    public void testUpdate(){
        //Create attribute
        AttributeName attributeName1 = new AttributeName();
        attributeName1.setName("heigth");

        //Record to DB
        AttributeName attributeName = service.add(attributeName1);

        assertThat(attributeName.getName(), is(equalTo("heigth")));
        assertThat(attributeName.getName(), isA(String.class));
        assertThat(attributeName, is(notNullValue()));
        assertThat(attributeName, is(anything()));
        assertThat(attributeName.getName(), is(greaterThan("")));
        assertThat(attributeName.getName(), is(not(lessThan(""))));
        assertThat(attributeName.getClass(), is(typeCompatibleWith(AttributeName.class)));

        attributeName1.setName("heigth");

        //Update
        service.update(attributeName1);

        //Get update info
        AttributeName attributeName2 = service.get(attributeName1.getName());

        assertThat(attributeName2.getName(), is(equalTo("heigth")));
        assertThat(attributeName2.getName(), isA(String.class));
        assertThat(attributeName2, is(notNullValue()));
        assertThat(attributeName2, is(anything()));
        assertThat(attributeName2.getName(), is(greaterThan("")));
        assertThat(attributeName2.getName(), is(not(lessThan(""))));
        assertThat(attributeName2.getClass(), is(typeCompatibleWith(AttributeName.class)));
    }

    @Test
    public void testGetAll(){
        AttributeName attributeName1 = new AttributeName();
        attributeName1.setName("attr1");
        AttributeName attributeName2 = new AttributeName();
        attributeName2.setName("attr2");
        AttributeName attributeName3 = new AttributeName();
        attributeName3.setName("attr3");

        service.add(attributeName1);
        service.add(attributeName2);
        service.add(attributeName3);

        List<AttributeName> attributeNames = service.getAll();

        assertThat(attributeNames, is(notNullValue()));
        assertThat(attributeNames, is(anything()));
        assertThat(attributeNames.get(0), isA(AttributeName.class));
    }
}

