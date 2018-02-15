package com.endre.java.ejb;

import com.endre.java.entity.Quiz;
import com.endre.java.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class QuizEjb {

    @PersistenceContext
    private EntityManager em;

    public long createQuiz(long subCategoryId, String question, String firstAnswer,
                           String secondAnswer, String thirdAnswer, String fourthAnswer,
                           int indexOfCorrectAnswer){

        SubCategory subCategory = em.find(SubCategory.class, subCategoryId);
        if (subCategory == null){
            throw new IllegalArgumentException("Could not find matching Subcategory!");
        }


        Quiz quiz = new Quiz();
        quiz.setQuestion(question);
        quiz.setFirstAnswer(firstAnswer);
        quiz.setSecondAnswer(secondAnswer);
        quiz.setThirdAnswer(thirdAnswer);
        quiz.setFourthAnswer(fourthAnswer);
        quiz.setIndexOfCorrectAnswer(indexOfCorrectAnswer);
        quiz.setSubCategory(subCategory);

        em.persist(quiz);

        return quiz.getId();
    }


    public List<Quiz> getQuizzes(){
        TypedQuery<Quiz> query = em.createQuery("select q from Quiz q", Quiz.class);
        return query.getResultList();
    }

    public Quiz getQuiz(long id){
        Quiz quiz = em.find(Quiz.class, id);
        if (quiz == null){
            throw new IllegalArgumentException("Could not find Quiz matching this id");
        }
        return quiz;
    }

    public List<Quiz> getRandomQuizzes(int n, long categoryId){
        List<Quiz> allQuizzes = getQuizzes();

        allQuizzes = allQuizzes.stream()
                .filter(q -> q.getSubCategory().getParent().getId().equals(categoryId))
                .collect(Collectors.toList());

        if (allQuizzes.size() < n){
            throw new IllegalArgumentException("Not that many Quizzes in the database");
        }

        Random random = new Random();
        Set<Integer> chosenNumbers = new HashSet<>();
        List<Quiz> quizzes = new ArrayList<>();

        while (chosenNumbers.size() < n){
            int k = random.nextInt(allQuizzes.size());
            if (chosenNumbers.contains(k)){
                continue;
            }
            chosenNumbers.add(k);
            quizzes.add(allQuizzes.get(k));
        }
        return quizzes;
    }
}
