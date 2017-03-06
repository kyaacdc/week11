package com.smarthouse.dao.implementation;

import com.smarthouse.dao.entity.AttributeName;

import javax.persistence.TypedQuery;
import java.util.List;

public class AttributeNameDaoImpl extends AbstractGenericJpaDAO<AttributeName, String> {

    @Override
    public AttributeName get(String name) {
        return getEm().find(AttributeName.class, name);
    }

    @Override
    public List<AttributeName> getAll() {
        TypedQuery<AttributeName> query = getEm()
                .createQuery("SELECT a FROM AttributeName a", AttributeName.class);
        return query.getResultList();
    }

}
