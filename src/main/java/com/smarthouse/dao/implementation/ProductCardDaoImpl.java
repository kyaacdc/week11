package com.smarthouse.dao.implementation;

import com.smarthouse.dao.entity.ProductCard;

import javax.persistence.TypedQuery;
import java.util.List;

public class ProductCardDaoImpl extends AbstractGenericJpaDAO<ProductCard, String> {

    @Override
    public ProductCard get(String sku) {
        return getEm().find(ProductCard.class, sku);    }

    @Override
    public List<ProductCard> getAll() {
        TypedQuery<ProductCard> query = getEm()
                .createQuery("SELECT p FROM ProductCard p", ProductCard.class);
        return query.getResultList();       }
}
