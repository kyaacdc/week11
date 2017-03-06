package com.smarthouse.dao;

import com.smarthouse.dao.entity.AttributeName;
import com.smarthouse.dao.entity.AttributeValue;
import com.smarthouse.dao.entity.Category;
import com.smarthouse.dao.entity.ProductCard;
import com.smarthouse.dao.implementation.AttributeValueDaoImpl;
import com.smarthouse.dao.implementation.CategoryDaoImpl;
import com.smarthouse.dao.implementation.ProductCardDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AttributeValueTest {

    private IDao<AttributeValue, Integer> serviceAttrVal;

    @Before
    public void init(){
        serviceAttrVal = new AttributeValueDaoImpl();
    }

    @After
    public void after(){
        serviceAttrVal = null;
    }

    @Test
    public void testSaveRecord(){

        IDao<Category, Integer> categoryProd = new CategoryDaoImpl();
        IDao<ProductCard, String> serviceProd = new ProductCardDaoImpl();
        Category category = new Category("name", "desc", null);
        categoryProd.addPersist(category);
        ProductCard productCard = new ProductCard("111", "name", 111, 1, 1, 123, "xxx", category);

        AttributeValue attributeValue1 = new AttributeValue();
        attributeValue1.setValue("red");
        attributeValue1.setAttributeName(new AttributeName("color"));
        attributeValue1.setProductCard(productCard);

        //Record to DB
        AttributeValue attributeValue = serviceAttrVal.add(attributeValue1);

        assertThat(attributeValue.getValue(), equalTo("red"));
        assertThat(attributeValue.getAttributeName().getName(), equalTo("color"));
        assertThat(attributeValue.getProductCard().getName(), equalTo("name"));
        assertThat(attributeValue.getProductCard().getCategory().getName(), equalTo("name"));
    }

    @Test
    public void testDeleteRecord(){
        IDao<Category, Integer> categoryProd = new CategoryDaoImpl();
        IDao<ProductCard, String> serviceProd = new ProductCardDaoImpl();
        Category category = new Category("name", "desc", null);
        categoryProd.addPersist(category);
        ProductCard productCard = new ProductCard("111", "name", 111, 1, 1, 123, "xxx", category);

        AttributeValue attributeValue1 = new AttributeValue();
        attributeValue1.setValue("red");
        attributeValue1.setAttributeName(new AttributeName("color"));
        attributeValue1.setProductCard(productCard);

        //Record to DB
        AttributeValue attributeValue = serviceAttrVal.add(attributeValue1);

        //Delete from DB
        serviceAttrVal.delete(attributeValue.getId());
    }


    @Test
    public void testSelect(){
        IDao<Category, Integer> categoryProd = new CategoryDaoImpl();
        IDao<ProductCard, String> serviceProd = new ProductCardDaoImpl();
        Category category = new Category("name", "desc", null);
        categoryProd.addPersist(category);
        ProductCard productCard = new ProductCard("111", "name", 111, 1, 1, 123, "xxx", category);

        AttributeValue attributeValue1 = new AttributeValue();
        attributeValue1.setValue("red");
        attributeValue1.setAttributeName(new AttributeName("color"));
        attributeValue1.setProductCard(productCard);

        //Record to DB
        AttributeValue attributeValue = serviceAttrVal.add(attributeValue1);

        //Get from DB
        AttributeValue attributeValueFromDB = serviceAttrVal.get(attributeValue.getId());

        assertThat(attributeValueFromDB.getAttributeName().getName(), is(equalTo("color")));
        assertThat(attributeValueFromDB.getValue(), is(equalTo("red")));
        assertThat(attributeValueFromDB.getValue(), isA(String.class));
        assertThat(attributeValueFromDB.getValue(), is(notNullValue()));
        assertThat(attributeValueFromDB, is(anything()));
        assertThat(attributeValueFromDB.getValue(), is(greaterThan("")));
        assertThat(attributeValue.getValue(), is(not(lessThan(""))));
        assertThat(attributeValue.getClass(), is(typeCompatibleWith(AttributeValue.class)));
    }

    @Test
    public void testUpdate(){
        IDao<Category, Integer> categoryProd = new CategoryDaoImpl();
        IDao<ProductCard, String> serviceProd = new ProductCardDaoImpl();
        Category category = new Category("name", "desc", null);
        categoryProd.addPersist(category);
        ProductCard productCard = new ProductCard("111", "name", 111, 1, 1, 123, "xxx", category);

        AttributeValue attributeValue1 = new AttributeValue();
        attributeValue1.setValue("red");
        attributeValue1.setAttributeName(new AttributeName("color"));
        attributeValue1.setProductCard(productCard);

        //Record to DB
        AttributeValue attributeValue = serviceAttrVal.add(attributeValue1);

        assertThat(attributeValue.getValue(), is(equalTo("red")));
        assertThat(attributeValue.getValue(), isA(String.class));
        assertThat(attributeValue, is(notNullValue()));
        assertThat(attributeValue, is(anything()));
        assertThat(attributeValue.getValue(), is(greaterThan("")));
        assertThat(attributeValue.getValue(), is(not(lessThan(""))));
        assertThat(attributeValue.getClass(), is(typeCompatibleWith(AttributeValue.class)));

        attributeValue.setValue("green");

        //Update
        serviceAttrVal.update(attributeValue);

        //Get update info
        AttributeValue attributeValue2 = serviceAttrVal.get(attributeValue.getId());

        assertThat(attributeValue2.getValue(), is(equalTo("green")));
        assertThat(attributeValue2.getValue(), isA(String.class));
        assertThat(attributeValue2, is(notNullValue()));
        assertThat(attributeValue2, is(anything()));
        assertThat(attributeValue2.getValue(), is(greaterThan("")));
        assertThat(attributeValue2.getValue(), is(not(lessThan(""))));
        assertThat(attributeValue2.getClass(), is(typeCompatibleWith(AttributeValue.class)));
    }

    @Test
    public void testGetAll(){
        IDao<Category, Integer> categoryProd = new CategoryDaoImpl();
        IDao<ProductCard, String> serviceProd = new ProductCardDaoImpl();
        Category category = new Category("name", "desc", null);
        categoryProd.addPersist(category);
        ProductCard productCard = new ProductCard("111", "name", 111, 1, 1, 123, "xxx", category);

        AttributeValue attributeValue1 = new AttributeValue();
        attributeValue1.setValue("red");
        attributeValue1.setAttributeName(new AttributeName("color"));
        attributeValue1.setProductCard(productCard);

        AttributeValue attributeValue2 = new AttributeValue();
        attributeValue2.setValue("blue");
        attributeValue2.setAttributeName(new AttributeName("color"));
        attributeValue2.setProductCard(productCard);

        AttributeValue attributeValue3 = new AttributeValue();
        attributeValue3.setValue("blue");
        attributeValue3.setAttributeName(new AttributeName("color"));
        attributeValue3.setProductCard(productCard);

        //Record to DB
        serviceAttrVal.add(attributeValue1);
        serviceAttrVal.add(attributeValue2);
        serviceAttrVal.add(attributeValue3);

        List<AttributeValue> attributeValues = serviceAttrVal.getAll();

        assertThat(attributeValues, is(notNullValue()));
        assertThat(attributeValues, is(anything()));
        assertThat(attributeValues.get(0), isA(AttributeValue.class));
        assertThat(attributeValues.get(0).getValue(), isA(String.class));        }

}


