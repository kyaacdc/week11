package com.smarthouse.dao.entity;


import com.smarthouse.service.libs.validators.Phone;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AttributeName {

    @Id
    @Phone
    private String name;

    public AttributeName(String name) {
        this.name = name;
    }

    public AttributeName() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        AttributeName attributeName =new AttributeName();
        attributeName.setName("^&$$^&$^fgfhf65675");
        System.out.println(attributeName.getName());

    }
}