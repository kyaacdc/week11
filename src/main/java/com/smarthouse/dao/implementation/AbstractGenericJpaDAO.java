package com.smarthouse.dao.implementation;

import com.smarthouse.dao.IDao;
import com.smarthouse.dao.entity.Visualization;

import javax.persistence.EntityManager;
import java.util.List;
import static com.smarthouse.dao.PersistenceManager.ENT;

public abstract class AbstractGenericJpaDAO<T,K> implements IDao<T,K> {

    private EntityManager em = ENT.getEntityManager();

    @Override
    public abstract T get(K id);

    @Override
    public abstract List<T> getAll();

    @Override
    public T add(T t){
        em.getTransaction().begin();
        T tt = em.merge(t);
        em.getTransaction().commit();
        return tt;
    }

    @Override
    public void addPersist(T t){
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    @Override
    public void update(T t){
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
    }

    @Override
    public void delete(K id){
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }
/*
    @Override
    public void deleteAll() {
        em.getTransaction().begin();
        em.clear();
        em.getTransaction().commit();
    }
*/
    @Override
    public EntityManager getEm() {
        return em;
    }
}

