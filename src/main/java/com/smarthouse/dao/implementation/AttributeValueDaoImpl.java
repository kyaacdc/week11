package com.smarthouse.dao.implementation;

import com.smarthouse.dao.entity.AttributeValue;

import javax.persistence.TypedQuery;
import java.util.List;

public class AttributeValueDaoImpl extends AbstractGenericJpaDAO<AttributeValue, Integer> {

    @Override
    public AttributeValue get(Integer id) {
        return getEm().find(AttributeValue.class, id);    }

    @Override
    public List<AttributeValue> getAll() {
        TypedQuery<AttributeValue> query = getEm()
                .createQuery("SELECT a FROM AttributeValue a", AttributeValue.class);
        return query.getResultList();    }
}
