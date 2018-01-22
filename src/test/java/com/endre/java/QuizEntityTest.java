package com.endre.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuizEntityTest extends EntityTestBase{


    @Test
    public void testQuiz() {
        Quiz quiz = new Quiz();
        quiz.setQuestion("Will this work?");
        quiz.setFirstAnswer("Yes");
        quiz.setSecondAnswer("No");
        quiz.setThirdAnswer("Maybe");
        quiz.setFourthAnswer("No idea");
        quiz.setIndexOfCorrectAnswer(0);

        assertFalse(persistInATransaction(quiz));
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


    private SubCategory addSubcategory(Category category, String subcategoryName){
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subcategoryName);

        category.getSubCategories().add(subCategory);
        subCategory.setParent(category);

        return subCategory;
    }


    private Quiz createQuiz(SubCategory subCategory, String question){
        Quiz quiz = new Quiz();
        quiz.setQuestion(question);
        quiz.setFirstAnswer("a");
        quiz.setSecondAnswer("b");
        quiz.setThirdAnswer("c");
        quiz.setFourthAnswer("d");
        quiz.setIndexOfCorrectAnswer(0);

        quiz.setSubCategory(subCategory);

        return quiz;

    }



    @Test
    public void testQueries() {
        Category jee = new Category();
        jee.setName("JEE");

        SubCategory jpa = addSubcategory(jee, "JPA");
        SubCategory ejb = addSubcategory(jee, "EJB");
        SubCategory jsf = addSubcategory(jee, "JSF");

        assertTrue(persistInATransaction(jee, jpa, ejb, jsf));

        Quiz a = createQuiz(jpa, "a");
        Quiz b = createQuiz(jpa, "b");
        Quiz c = createQuiz(ejb, "c");
        Quiz d = createQuiz(jsf, "d");

        assertTrue(persistInATransaction(a,b,c,d));

        TypedQuery<Quiz> queryJPA = em.createQuery(
                "select q from Quiz q where q.subCategory = ?1", Quiz.class);
        queryJPA.setParameter(1, jpa);
        List<Quiz> jpaQuizzes = queryJPA.getResultList();
        assertEquals(2, jpaQuizzes.size());
        assertTrue(jpaQuizzes.stream().anyMatch(q -> q.getQuestion().equals("a")));
        assertTrue(jpaQuizzes.stream().anyMatch(q -> q.getQuestion().equals("b")));

        TypedQuery<Quiz> queryJEE = em.createQuery(
                "select q from Quiz q where q.subCategory.parent = ?1", Quiz.class);
        queryJEE.setParameter(1, jee);
        List<Quiz> jeeQuizzes = queryJEE.getResultList();
        assertEquals(4, jeeQuizzes.size());

    }
}
