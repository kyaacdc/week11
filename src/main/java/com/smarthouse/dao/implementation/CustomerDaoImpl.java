package com.smarthouse.dao.implementation;

import com.smarthouse.dao.entity.Customer;

import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerDaoImpl extends AbstractGenericJpaDAO<Customer, String> {
    @Override
    public Customer get(String email) {
        return getEm().find(Customer.class, email);    }

    @Override
    public List<Customer> getAll() {
        TypedQuery<Customer> query = getEm()
                .createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();        }
}
