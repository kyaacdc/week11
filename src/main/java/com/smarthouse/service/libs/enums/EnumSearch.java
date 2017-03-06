package com.smarthouse.service.libs.enums;

import com.smarthouse.dao.IDao;
import com.smarthouse.dao.entity.ProductCard;
import com.smarthouse.dao.implementation.ProductCardDaoImpl;

import java.util.*;

public enum EnumSearch {

    FIND_ALL,
    FIND_BY_NAME,
    FIND_IN_PROD_DESC,
    FIND_IN_CATEGORY_NAME,
    FIND_IN_CATEGORY_DESC;

    private Set<ProductCard> set;

    public  Set<ProductCard> findAll (String criteria)
    {
        Set<ProductCard> result = new LinkedHashSet<>();
        IDao<ProductCard, String> pdao = new ProductCardDaoImpl();
        List<ProductCard> all = pdao.getAll();

        set = findByName(criteria, all);
        result.addAll(set);
        set = findInProductDescription(criteria, all);
        result.addAll(set);
        set = findInCategoryDescription(criteria, all);
        result.addAll(set);
        set = findInCategoryName(criteria, all);
        result.addAll(set);

        return result;
    }

    public  Set<ProductCard> findByPlace
            (String criteria, List<ProductCard> all, EnumSearch placeForFind)
    {
        switch (placeForFind) {
            case FIND_BY_NAME:
                return findByName(criteria, all);
            case FIND_IN_PROD_DESC:
                return findInProductDescription(criteria, all);
            case FIND_IN_CATEGORY_NAME:
                return findInCategoryName(criteria, all);
            case FIND_IN_CATEGORY_DESC:
                return findInCategoryDescription(criteria, all);
            default:
                throw new NoSuchElementException();
        }
    }


    public Set<ProductCard> findByName(String criteria, List<ProductCard> all){
        set = new LinkedHashSet<>();
        for(ProductCard p: all) {
            String name = p.getName();
            if (name.equals(criteria))
                set.add(p);
        }
        return set;
    }

    public Set<ProductCard> findInProductDescription(String criteria, List<ProductCard> all){
        set = new LinkedHashSet<>();
        for(ProductCard p: all){
            String name = p.getProductDescription();
            if(name.equals(criteria))
                set.add(p);
        }
        return set;
    }

    public Set<ProductCard> findInCategoryName(String criteria, List<ProductCard> all){
        set = new LinkedHashSet<>();
        for(ProductCard p: all){
            String name = p.getCategory().getName();
            if(name.equals(criteria)) {
                set.add(p);
            }
        }
        return set;
    }

    public Set<ProductCard> findInCategoryDescription(String criteria, List<ProductCard> all){
        set = new LinkedHashSet<>();
        for(ProductCard p: all){
            String name = p.getCategory().getDescription();
            if(name.equals(criteria)) {
                set.add(p);
            }
        }
        return set;
    }
}
