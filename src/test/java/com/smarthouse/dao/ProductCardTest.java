package com.smarthouse.dao;

import com.smarthouse.dao.entity.Category;
import com.smarthouse.dao.entity.ProductCard;
import com.smarthouse.dao.entity.Visualization;
import com.smarthouse.dao.implementation.ProductCardDaoImpl;
import com.smarthouse.dao.implementation.VisualizationDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ProductCardTest {

    private IDao<ProductCard, String> service;

    @Before
    public void init() {
        service = new ProductCardDaoImpl();
    }

    @After
    public void after() {
        service = null;
    }

    @Test
    public void testSaveRecord() {
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        //Record to DB
        ProductCard productCard = service.add(productCard1);

        assertThat(productCard.getAmount(), is(equalTo(34)));
        assertThat(productCard.getPrice(), is(equalTo(2222)));
        assertThat(productCard.getLikes(), is(equalTo(45)));
        assertThat(productCard.getDislikes(), is(equalTo(4)));
        assertThat(productCard.getAmount(), isA(Integer.class));
        assertThat(productCard, is(notNullValue()));
        assertThat(productCard, is(anything()));
        assertThat(productCard.getPrice(), is(greaterThan(2000)));
        assertThat(productCard.getAmount(), is(not(lessThan(4))));
        assertThat(productCard.getClass(), is(typeCompatibleWith(ProductCard.class)));
    }

    @Test
    public void testDeleteRecord() {
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        //Record to DB
        ProductCard productCard = service.add(productCard1);

        IDao<Visualization, Integer> vis = new VisualizationDaoImpl();
        List<Visualization> list = vis.getAll().stream().
                filter(a -> a.getProductCard().getSku().equals(productCard1.getSku()))
                .collect(Collectors.toList());
        for(Visualization v: list)
            vis.delete(v.getId());

        //Delete from DB
        service.delete(productCard.getSku());
    }

    @Test
    public void testSelect() {
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        //Record to DB
        ProductCard productCard = service.add(productCard1);

        //Get from DB
        ProductCard productCardFromDB = service.get(productCard.getSku());

        assertThat(productCardFromDB.getPrice(), is(equalTo(2222)));
        assertThat(productCardFromDB.getCategory().getName(), is(equalTo("name")));
        assertThat(productCardFromDB.getPrice(), isA(Integer.class));
        assertThat(productCardFromDB, is(notNullValue()));
        assertThat(productCardFromDB, is(anything()));
        assertThat(productCardFromDB.getClass(), is(typeCompatibleWith(ProductCard.class)));
    }

    @Test
    public void testUpdate() {
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        //Record to DB
        ProductCard productCard = service.add(productCard1);

        assertThat(productCard.getAmount(), is(equalTo(34)));
        assertThat(productCard.getPrice(), is(equalTo(2222)));
        assertThat(productCard.getLikes(), is(equalTo(45)));
        assertThat(productCard.getDislikes(), is(equalTo(4)));
        assertThat(productCard.getAmount(), isA(Integer.class));
        assertThat(productCard, is(notNullValue()));
        assertThat(productCard, is(anything()));
        assertThat(productCard.getPrice(), is(greaterThan(2000)));
        assertThat(productCard.getAmount(), is(not(lessThan(4))));
        assertThat(productCard.getClass(), is(typeCompatibleWith(ProductCard.class)));

        productCard.setAmount(6);

        //Update
        service.update(productCard);

        //Get update info
        ProductCard productCard2 = service.add(productCard);

        assertThat(productCard2.getAmount(), is(equalTo(6)));
    }

    @Test
    public void testGetAll(){
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);
        ProductCard productCard2 = new ProductCard("333", "3name", 3333, 34, 45, 4, "xxx", category);
        ProductCard productCard3 = new ProductCard("444", "4name", 444, 34, 45, 4, "xxx", category);

        //Record to DB
        service.add(productCard1);
        service.add(productCard2);
        service.add(productCard3);



        List<ProductCard> productCards = service.getAll();

        assertThat(productCards, is(notNullValue()));
        assertThat(productCards, is(anything()));
        assertThat(productCards.get(1), isA(ProductCard.class));
    }
}


