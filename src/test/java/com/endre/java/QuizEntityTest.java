package com.endre.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertTrue;

public class QuizEntityTest {


    private EntityManager em;
    private EntityManagerFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("DB");
        em = factory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        em.close();
        factory.close();
    }


    private boolean persistInATransaction(Object... obj) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (Object o : obj) {
                em.persist(o);
            }
            tx.commit();
        } catch (Exception e){
            System.out.println("FAILED TRANSACTION: " + e.toString());
            tx.rollback();
            return false;
        }
        return true;
    }

    @Test
    public void testQuiz() {
        Quiz quiz = new Quiz();
        quiz.setQuestion("Will this work?");
        quiz.setFirstAnswer("Yes");
        quiz.setSecondAnswer("No");
        quiz.setThirdAnswer("Maybe");
        quiz.setFourthAnswer("No idea");
        quiz.setIndexOfCorrectAnswer(0);

        assertTrue(persistInATransaction(quiz));
    }


    @Test
    public void testQuizWithSubcategory() {
        Category category = new Category();
        category.setName("Car");

        SubCategory subCategory = new SubCategory();
        subCategory.setName("Car Brand");

        category.getSubCategories().add(subCategory);
        subCategory.setParent(category);

        Quiz carQuiz = new Quiz();
        carQuiz.setQuestion("Out of these car brands witch has a logo with a Horse in it?");
        carQuiz.setFirstAnswer("Ferrari");
        carQuiz.setSecondAnswer("BMW");
        carQuiz.setThirdAnswer("VW");
        carQuiz.setFourthAnswer("Fiat");
        carQuiz.setIndexOfCorrectAnswer(0);

        carQuiz.setSubCategory(subCategory);

        assertTrue(persistInATransaction(category, subCategory, carQuiz));
    }
}
