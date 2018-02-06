package com.endre.java.ejb;

import com.endre.java.entity.Category;
import com.endre.java.entity.SubCategory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


import javax.ejb.EJB;
import java.util.List;

@RunWith(Arquillian.class)
public class CategoryEjbTest {

    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "com.endre.java")
                .addAsResource("META-INF/persistence.xml");
    }


    @EJB
    private CategoryEjb ctgEjb;

    @EJB
    private ResetEjb resetEjb;

    @Before
    public void setUp() throws Exception {
        resetEjb.resetDatabase();
    }

    @Test
    public void testNoCategory() {
        List<Category> list = ctgEjb.getAllCategories(false);

        assertEquals(0, list.size());
    }

    @Test
    public void testCreateCategory() {
        Long id = ctgEjb.createCategory("cars");
        assertNotNull(id);
    }


    @Test
    public void testGetCategory() {
        String name = "cars";
        Long id = ctgEjb.createCategory("cars");
        assertEquals(name, ctgEjb.getCategory(id, false).getName());
    }


    @Test
    public void testCreateSubCategory() throws IllegalAccessException {
        String categoryName = "cars";
        String subCategoryName = "bmw";
        Long categoryId = ctgEjb.createCategory(categoryName);
        Long subCategoryId = ctgEjb.createSubCategory(categoryId, subCategoryName);

        SubCategory subCategory = ctgEjb.getSubcategory(subCategoryId);

        assertEquals(subCategoryName, subCategory.getName());
        assertEquals(categoryId, subCategory.getParent().getId());
    }


    @Test
    public void testGetAllCategories() throws IllegalAccessException {
        Long cA = ctgEjb.createCategory("cA");
        Long cB = ctgEjb.createCategory("cB");
        Long cC = ctgEjb.createCategory("cC");

        Long sA = ctgEjb.createSubCategory(cA, "sA");
        Long sB = ctgEjb.createSubCategory(cB, "sB");
        Long sC = ctgEjb.createSubCategory(cC, "sC");

        List<Category> categories = ctgEjb.getAllCategories(false);
        assertEquals(3, categories.size());

        Category firstCategory = categories.get(0);

        try {
            firstCategory.getSubCategories().size();
            fail();
        }catch (Exception e){
            //exception
        }

        categories = ctgEjb.getAllCategories(true);

        firstCategory = categories.get(0);

        assertEquals(1, firstCategory.getSubCategories().size());
    }

    @Test
    public void testCreateTwice() {
        String name = "cars";
        ctgEjb.createCategory(name);
        try {
            ctgEjb.createCategory(name);
            fail();
        }catch (Exception e){
            //exception
        }
    }
}
