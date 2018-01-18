package com.endre.java;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Quiz {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private SubCategory subCategory;

    private String question;
    private String firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;
    private int indexOfCorrectAnswer;


    public Quiz() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String ans1) {
        this.firstAnswer = ans1;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String ans2) {
        this.secondAnswer = ans2;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String ans3) {
        this.thirdAnswer = ans3;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String ans4) {
        this.fourthAnswer = ans4;
    }

    public int getIndexOfCorrectAnswer() {
        return indexOfCorrectAnswer;
    }

    public void setIndexOfCorrectAnswer(int currentAns) {
        this.indexOfCorrectAnswer = currentAns;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
