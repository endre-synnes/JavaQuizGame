package com.endre.java;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class QuizEntityTest {


    @Test
    public void testQuiz() {
        Quiz quiz = new Quiz();
        quiz.setQuestion("Will this work?");
        quiz.setAnsOne("Yes");
        quiz.setAnsTwo("No");
        quiz.setAnsThree("Maybe");
        quiz.setAnsFour("No idea");
        quiz.setCurrentAns(0);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DB");
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(quiz);

        tx.commit();
    }
}
