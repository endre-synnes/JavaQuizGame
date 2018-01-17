package com.endre.java;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class QuizEntityTest {


    private EntityManager em;
    private EntityTransaction tx;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DB");
        em = factory.createEntityManager();
        tx = em.getTransaction();
    }

    @Test
    public void testQuiz() {
        Quiz quiz = new Quiz();
        quiz.setQuestion("Will this work?");
        quiz.setAnsOne("Yes");
        quiz.setAnsTwo("No");
        quiz.setAnsThree("Maybe");
        quiz.setAnsFour("No idea");
        quiz.setCurrentAns(0);


        tx.begin();
        em.persist(quiz);
        tx.commit();
    }
}
