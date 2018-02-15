package com.endre.java.ejb;

import com.endre.java.entity.Quiz;
import com.endre.java.entity.SubCategory;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class QuizEjbTest extends EjbTestBase {


    @EJB
    private CategoryEjb categoryEjb;

    @EJB
    private QuizEjb quizEjb;

    private long createCtgAndSub(String ctg, String sub){
        long ctgId = categoryEjb.createCategory(ctg);
        return categoryEjb.createSubCategory(ctgId, sub);
    }


    @Test
    public void testNoQuiz() {
        assertEquals(0, quizEjb.getQuizzes().size());
    }


    @Test
    public void testCreateQuiz() {

        long subId = createCtgAndSub("Cars and stuff", "Car brands and more");

        String question = "What does VW mean?";

        long quizId = quizEjb.createQuiz(subId, question,
                "Valke Wogen",
                "foo",
                "Volks Wagen",
                "bar",
                2);

        assertEquals(1, quizEjb.getQuizzes().size());
        assertEquals(question, quizEjb.getQuiz(quizId).getQuestion());
    }


    @Test
    public void testNotEnoughQuizzes() {

        long ctgId = createQuizzes("a", "b", "c");


        try {
            quizEjb.getRandomQuizzes(5, ctgId);
            fail();
        }catch (Exception e){
            //exception (this should fail
        }

    }

    private long createQuizzes(String... names){

        long subId = createCtgAndSub("foo", "bar");

        for (String n: names) {
            quizEjb.createQuiz(subId,
                    n,
                    "yes",
                    "no",
                    "maybe",
                    "noe idea",
                    1);
        }

        return categoryEjb.getSubcategory(subId).getParent().getId();
    }

    @Test
    public void testGetRandomQuizzes() {

        long ctgId = createQuizzes("a", "b", "c");

        Set<String> questions = new HashSet<>();

        for (int i = 0; i < 50; i++) {

            List<Quiz> quizzes = quizEjb.getRandomQuizzes(2, ctgId);
            assertEquals(2, quizzes.size());

            Quiz first = quizzes.get(0);
            Quiz second = quizzes.get(1);

            assertNotEquals(first.getQuestion(), second.getQuestion());

            questions.add(first.getQuestion());
            questions.add(second.getQuestion());
        }

        assertEquals(3, questions.size());
        assertTrue(questions.contains("a"));
        assertTrue(questions.contains("b"));
        assertTrue(questions.contains("c"));
    }
}
