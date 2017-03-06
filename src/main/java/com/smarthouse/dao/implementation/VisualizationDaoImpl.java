package com.smarthouse.dao.implementation;

import com.smarthouse.dao.entity.Visualization;

import javax.persistence.TypedQuery;
import java.util.List;

public class VisualizationDaoImpl extends AbstractGenericJpaDAO<Visualization, Integer> {

    @Override
    public Visualization get(Integer id) {
        return getEm().find(Visualization.class, id);
    }


    @Override
    public List<Visualization> getAll() {
        TypedQuery<Visualization> query = getEm()
                .createQuery("SELECT v FROM Visualization v", Visualization.class);
        return query.getResultList();
    }
}
