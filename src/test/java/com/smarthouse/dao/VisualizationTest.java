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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class VisualizationTest {

    private IDao<Visualization, Integer> service;

    @Before
    public void init() {
        service = new VisualizationDaoImpl();
    }

    @After
    public void after() {
        service = null;
    }

    @Test
    public void testSaveRecord() {
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        Visualization visualization1 = new Visualization(2, "url", productCard1);
        //Record to DB
        Visualization visualization = service.add(visualization1);

        assertThat(visualization.getUrl(), is(equalTo("url")));
        assertThat(visualization.getType(), is(equalTo(2)));
        assertThat(visualization.getProductCard().getSku(), is(equalTo("222")));
        assertThat(visualization.getProductCard().getCategory().getName(), is(equalTo("name")));
        assertThat(visualization.getType(), isA(Integer.class));
        assertThat(visualization, is(notNullValue()));
        assertThat(visualization, is(anything()));
        assertThat(visualization.getClass(), is(typeCompatibleWith(Visualization.class)));
    }

    @Test
    public void testDeleteRecord() {
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        Visualization visualization1 = new Visualization(2, "url", productCard1);

        Visualization visualization = service.add(visualization1);

        //Delete from DB
        service.delete(visualization.getId());
    }

    @Test
    public void testSelect() {
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        Visualization visualization1 = new Visualization(2, "url", productCard1);

        Visualization visualization = service.add(visualization1);
        //Get from DB
        Visualization visualizationFromDB = service.get(visualization.getId());

        assertThat(visualizationFromDB.getUrl(), is(equalTo("url")));
        assertThat(visualizationFromDB.getType(), is(equalTo(2)));
        assertThat(visualizationFromDB.getProductCard().getSku(), is(equalTo("222")));
        assertThat(visualizationFromDB.getProductCard().getCategory().getName(), is(equalTo("name")));
        assertThat(visualizationFromDB.getType(), isA(Integer.class));
        assertThat(visualizationFromDB, is(notNullValue()));
        assertThat(visualizationFromDB, is(anything()));
        assertThat(visualization.getClass(), is(typeCompatibleWith(Visualization.class)));
    }

    @Test
    public void testUpdate() {
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        Visualization visualization1 = new Visualization(2, "url", productCard1);

        Visualization visualization = service.add(visualization1);

        assertThat(visualization.getUrl(), is(equalTo("url")));
        assertThat(visualization.getType(), is(equalTo(2)));
        assertThat(visualization.getProductCard().getSku(), is(equalTo("222")));
        assertThat(visualization.getProductCard().getCategory().getName(), is(equalTo("name")));
        assertThat(visualization.getType(), isA(Integer.class));
        assertThat(visualization, is(notNullValue()));
        assertThat(visualization, is(anything()));
        assertThat(visualization.getClass(), is(typeCompatibleWith(Visualization.class)));

        visualization.setUrl("newUrl");

        //Update
        service.update(visualization);

        //Get update info
        Visualization visualization2 = service.add(visualization);

        assertThat(visualization2.getUrl(), is(equalTo("newUrl")));
    }

    @Test
    public void testGetAll(){
        Category category = new Category("name", "desc", null);
        ProductCard productCard1 = new ProductCard("222", "2name", 2222, 34, 45, 4, "xxx", category);

        Visualization visualization1 = new Visualization(2, "1url", productCard1);
        Visualization visualization2 = new Visualization(4, "2url", productCard1);
        Visualization visualization3 = new Visualization(6, "3url", productCard1);

        service.add(visualization1);
        service.add(visualization2);
        service.add(visualization3);

        List<Visualization> visualizations = service.getAll();

       assertThat(visualizations, is(notNullValue()));
       assertThat(visualizations, is(anything()));
       assertThat(visualizations.get(0), isA(Visualization.class));
    }
}


