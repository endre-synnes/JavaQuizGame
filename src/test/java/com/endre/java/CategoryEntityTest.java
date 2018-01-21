package com.endre.java;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;

import static org.junit.Assert.*;

public class CategoryEntityTest {

    private EntityManagerFactory factory;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("DB");
        em = factory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        em = null;
        factory = null;
    }

    private boolean persist(Object... obj){
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (Object o : obj) {
                em.persist(o);
            }
            tx.commit();
        }catch (Exception e){
            System.out.println("COULD NOT PERSIST!");
            tx.rollback();
            return false;
        }
        return true;
    }


    @Test
    public void testTooLongName(){
        Category cars = new Category();
        cars.setName("frgtyuikjhgfvbnmuhygtfrderegtrhytykiggggggu" +
                "jyhrtgerfretgryhtjyijutyhgrtfrreetbrhyjtyhrtgetr" +
                "hyjtyhrtggetgrhytjnybrtvebrntmntbrvetryuyntbgvff" +
                "fdrgfthftgdrfsefesgrdhtfjygkujyhtgrfrrgthsyjukiu");
        assertFalse(persist(cars));

        Category bikes = new Category();
        bikes.setName("Bikes");
        assertTrue(persist(bikes));
    }

}
