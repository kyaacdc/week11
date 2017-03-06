package com.smarthouse.dao.implementation;

import com.smarthouse.dao.entity.OrderMain;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderMainDaoImpl extends AbstractGenericJpaDAO<OrderMain, Integer> {
    @Override
    public OrderMain get(Integer id) {
        OrderMain orderMain = getEm().find(OrderMain.class, id);
        if (orderMain == null)
            throw new NoResultException("This Order not exist");
        return orderMain;    }

    @Override
    public List<OrderMain> getAll() {
        TypedQuery<OrderMain> query = getEm()
                .createQuery("SELECT o FROM OrderMain o", OrderMain.class);
        return query.getResultList();          }
}
