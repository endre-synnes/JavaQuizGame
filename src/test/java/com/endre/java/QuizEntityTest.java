package com.endre.java;

import junit.framework.TestCase;
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
        quiz.setAns1("Yes");
        quiz.setAns2("No");
        quiz.setAns3("Maybe");
        quiz.setAns4("No idea");
        quiz.setCurrentAns(0);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DB");
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(quiz);

        tx.commit();
    }
}
