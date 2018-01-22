package com.endre.java;

import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class EntityTestBase {

    protected EntityManager em;
    private EntityManagerFactory factory;

    @Before
    public void init() throws Exception{
        factory = Persistence.createEntityManagerFactory("DB");
        em = factory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        em.close();
        factory.close();
    }


    protected boolean persistInATransaction(Object... obj){
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (Object o : obj) {
                em.persist(o);
            }
            tx.commit();
        }catch (Exception e){
            System.out.println("FAILED TRANSACTION: " + e.toString());
            tx.rollback();
            return false;
        }
        return true;
    }

}
