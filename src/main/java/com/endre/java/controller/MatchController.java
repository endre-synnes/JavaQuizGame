package com.endre.java.controller;

import com.endre.java.ejb.CategoryEjb;
import com.endre.java.ejb.QuizEjb;
import com.endre.java.entity.Category;
import com.endre.java.entity.Quiz;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class MatchController implements Serializable {


    @EJB
    private CategoryEjb categoryEjb;


    @EJB
    private QuizEjb quizEjb;


    private final int NUMBER_QUIZZES = 3;

    private boolean gameIsOn = false;
    private Long selectedCategoryId;
    private int counter;
    private List<Quiz> questions;


    public boolean isMatchOn(){
        return gameIsOn;
    }


    public String newMatch(){
        gameIsOn = true;

        selectedCategoryId = null;

        return "/ui/match.jsf&faces-redirect=true";
    }


    public List<Category> getCategories(){
        return categoryEjb.getAllCategories(false);
    }


    public boolean isCategorySelected(){
        return selectedCategoryId != null;
    }


    public void selectCategory(long id){
        selectedCategoryId = id;
        counter = 0;
        questions = quizEjb.getRandomQuizzes(NUMBER_QUIZZES, selectedCategoryId);
    }


    public Quiz getCurrentQuiz(){
        return questions.get(counter);
    }

    public String answerQuiz(int index){
        Quiz quiz = getCurrentQuiz();
        if (index == quiz.getIndexOfCorrectAnswer()){
            counter++;
            if (counter == NUMBER_QUIZZES){
                gameIsOn = false;
                return "result.jsf?victory=true&faces-redirect=true";
            }
        } else {
            gameIsOn = false;
            return "result.jsf?defeat=true&faces-redirect=true";
        }

        return null;
    }

    public int getIncreasedCounter(){
        return counter + 1;
    }

    public int getNumberOfQuizzes(){
        return NUMBER_QUIZZES;
    }




}
