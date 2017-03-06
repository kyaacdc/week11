package com.smarthouse.dao;

import javax.persistence.EntityManager;
import java.util.List;


public interface IDao <T,K>{

    T get(K id);

    List<T> getAll();

    T add(T t);

    void addPersist(T t);

    void update(T t);

    void delete(K id);

    EntityManager getEm();
}
