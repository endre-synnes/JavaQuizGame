package com.endre.java.ejb;

import com.endre.java.entity.Quiz;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
}
