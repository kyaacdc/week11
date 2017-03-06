package com.smarthouse.dao.implementation;

import com.smarthouse.dao.entity.Category;

import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDaoImpl extends AbstractGenericJpaDAO<Category, Integer> {

    @Override
    public Category get(Integer id) {
        return getEm().find(Category.class, id);    }

    @Override
    public List<Category> getAll() {
        TypedQuery<Category> query = getEm()
                .createQuery("SELECT c FROM Category c", Category.class);
        return query.getResultList();    }
}
