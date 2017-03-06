package com.smarthouse.dao;

import com.smarthouse.dao.entity.Category;
import com.smarthouse.dao.implementation.CategoryDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CategoryTest {

    private IDao<Category, Integer> service;

    @Before
    public void init(){
        service = new CategoryDaoImpl();
    }

    @After
    public void after(){
        service = null;
    }

    @Test
    public void testSaveRecord(){
        Category category1 = new Category("catname", "catdesc", null);

        //Record to DB
        Category category = service.add(category1);

        assertThat(category.getName(), is(equalTo("catname")));
        assertThat(category.getName(), isA(String.class));
        assertThat(category, is(notNullValue()));
        assertThat(category, is(anything()));
        assertThat(category.getName(), is(greaterThan("")));
        assertThat(category.getName(), is(not(lessThan(""))));
        assertThat(category.getClass(), is(typeCompatibleWith(Category.class)));

        assertThat(category.getDescription(), is(equalTo("catdesc")));
        assertThat(category.getDescription(), isA(String.class));
        assertThat(category.getDescription(), is(greaterThan("")));
        assertThat(category.getDescription(), is(not(lessThan(""))));
    }

    @Test
    public void testDeleteRecord(){
        Category category1 = new Category("catname", "catdesc", null);

        //Record to DB
        Category category = service.add(category1);
        //Delete from DB
        service.delete(category.getId());
    }

    @Test
    public void testSelect(){
        Category category1 = new Category("catname", "catdesc", null);

        //Record to DB
        Category category = service.add(category1);

        //Get from DB
        Category categoryFromDB = service.get(category.getId());

        assertThat(categoryFromDB.getName(), is(equalTo("catname")));
        assertThat(categoryFromDB.getDescription(), is(equalTo("catdesc")));
        assertThat(categoryFromDB.getName(), isA(String.class));
        assertThat(categoryFromDB, is(notNullValue()));
        assertThat(categoryFromDB, is(anything()));
        assertThat(categoryFromDB.getName(), is(greaterThan("")));
        assertThat(categoryFromDB.getName(), is(not(lessThan(""))));
        assertThat(categoryFromDB.getClass(), is(typeCompatibleWith(Category.class)));
    }

    @Test
    public void testSelectRecursive(){
        Category category1 = new Category("catname1", "catdesc1", null);
        Category category2 = new Category("catname21", "catdesc2", category1);
        Category category3 = new Category("catname31", "catdesc3", category1);
        Category category4 = new Category("catname42", "catdesc4", category2);

        //Record to DB
        service.add(category1);
        service.add(category2);
        service.add(category3);
        Category category = service.add(category4);

        //Get from DB
        Category categoryFromDB = service.get(category.getId());

        assertThat(categoryFromDB.getName(), is(equalTo("catname42")));
        assertThat(categoryFromDB.getDescription(), is(equalTo("catdesc4")));
        assertThat(categoryFromDB.getName(), isA(String.class));
        assertThat(categoryFromDB, is(notNullValue()));
        assertThat(categoryFromDB, is(anything()));
        assertThat(categoryFromDB.getName(), is(greaterThan("")));
        assertThat(categoryFromDB.getName(), is(not(lessThan(""))));
        assertThat(categoryFromDB.getClass(), is(typeCompatibleWith(Category.class)));
    }

    @Test
    public void testUpdate(){
        Category category1 = new Category("catname", "catdesc", null);

        //Record to DB
        Category category = service.add(category1);

        assertThat(category.getName(), is(equalTo("catname")));
        assertThat(category.getName(), isA(String.class));
        assertThat(category, is(notNullValue()));
        assertThat(category, is(anything()));
        assertThat(category.getName(), is(greaterThan("")));
        assertThat(category.getName(), is(not(lessThan(""))));
        assertThat(category.getClass(), is(typeCompatibleWith(Category.class)));

        category1.setName("newcatname");

        //Update
        service.update(category1);

        //Get update info
        Category category2 = service.add(category1);

        assertThat(category2.getName(), is(equalTo("newcatname")));
        assertThat(category2.getName(), isA(String.class));
        assertThat(category2, is(notNullValue()));
        assertThat(category2, is(anything()));
        assertThat(category2.getName(), is(greaterThan("")));
        assertThat(category2.getName(), is(not(lessThan(""))));
        assertThat(category2.getClass(), is(typeCompatibleWith(Category.class)));
    }

    @Test
    public void testGetAll(){
        Category category1 = new Category("1catname", "1catdesc", null);
        Category category2 = new Category("2catname", "2catdesc", null);
        Category category3 = new Category("3catname", "3catdesc", null);

        //Record to DB
        service.add(category1);
        service.add(category2);
        service.add(category3);


        List<Category> categories = service.getAll();

        assertThat(categories, is(notNullValue()));
        assertThat(categories, is(anything()));
        assertThat(categories.get(0), isA(Category.class));
    }
}


