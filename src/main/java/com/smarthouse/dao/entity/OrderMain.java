package com.smarthouse.dao.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
public class OrderMain {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    private int orderid;

    private String address;

    private int status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "customer", nullable = false)
    Customer customer;

    public OrderMain(String address, int status, Customer customer) {
        this.address = address;
        this.status = status;
        this.customer = customer;
    }

    public OrderMain() {
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}