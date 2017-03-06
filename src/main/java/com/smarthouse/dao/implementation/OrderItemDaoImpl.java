package com.smarthouse.dao.implementation;

import com.smarthouse.dao.entity.OrderItem;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderItemDaoImpl extends AbstractGenericJpaDAO<OrderItem, Integer> {
    @Override
    public OrderItem get(Integer id) {
        OrderItem orderItem = getEm().find(OrderItem.class, id);
        if (orderItem == null)
            throw new NoResultException("This Item Order not exist");
        return orderItem;
    }


    @Override
    public List<OrderItem> getAll() {
        TypedQuery<OrderItem> query = getEm()
                .createQuery("SELECT i FROM OrderItem i", OrderItem.class);
        return query.getResultList();
    }
}