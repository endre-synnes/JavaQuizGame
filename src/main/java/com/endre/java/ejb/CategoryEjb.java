package com.endre.java.ejb;

import com.endre.java.Category;
import com.endre.java.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
public class CategoryEjb{

    @PersistenceContext
    private EntityManager em;

    public CategoryEjb(){}

    public Long createCategory(@NotNull String name){
        //TODO lage en test for om kategori eksisterer

        Category category = new Category();
        category.setName(name);

        em.persist(category);
        return category.getId();
    }


    public Long createSubCategory(Long parentId, String name) throws IllegalAccessException {

        Category category = em.find(Category.class, parentId);
        if (category == null){
            throw new IllegalAccessException("Could not find matching parent Category");
        }
        SubCategory subCategory = new SubCategory();
        subCategory.setParent(category);
        subCategory.setName(name);

        category.getSubCategories().add(subCategory);

        em.persist(subCategory);

        return subCategory.getId();
    }



    public List<Category> getAllCategories(boolean withSub){
        TypedQuery<Category> query = em.createQuery("select c from Category c", Category.class);
        List<Category> categories = query.getResultList();

        if (withSub){
            //force loading
            categories.forEach(c -> c.getSubCategories().size());
        }

        return categories;
    }


    public Category getCategory(long id, boolean withSub){
        Category category = em.find(Category.class, id);
        if (withSub && category != null){
            category.getSubCategories().size();
        }
        return category;
    }


    public SubCategory getSubcategory(long id){
        return em.find(SubCategory.class, id);
    }



}
