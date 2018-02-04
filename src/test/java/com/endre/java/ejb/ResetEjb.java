package com.endre.java.ejb;

import com.endre.java.entity.Category;
import com.endre.java.entity.Quiz;
import com.endre.java.entity.SubCategory;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class ResetEjb {

    @PersistenceContext
    private EntityManager em;


    public void resetDatabase(){
        deleteEntities(Quiz.class);
        deleteEntities(SubCategory.class);
        deleteEntities(Category.class);
    }

    private void deleteEntities(Class<?> entity) {

        if (entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non-entity class");
        }

        String name = entity.getSimpleName();

        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();


    }
}
