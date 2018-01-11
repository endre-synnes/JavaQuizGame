package com.endre.java;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Quiz {

    @Id @GeneratedValue
    private Long id;

    private String question;
    private String ansOne, ansTwo, ansThree, ansFour;
    private int currentAns;

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

    public String getAnsOne() {
        return ansOne;
    }

    public void setAnsOne(String ans1) {
        this.ansOne = ans1;
    }

    public String getAnsTwo() {
        return ansTwo;
    }

    public void setAnsTwo(String ans2) {
        this.ansTwo = ans2;
    }

    public String getAnsThree() {
        return ansThree;
    }

    public void setAnsThree(String ans3) {
        this.ansThree = ans3;
    }

    public String getAnsFour() {
        return ansFour;
    }

    public void setAnsFour(String ans4) {
        this.ansFour = ans4;
    }

    public int getCurrentAns() {
        return currentAns;
    }

    public void setCurrentAns(int currentAns) {
        this.currentAns = currentAns;
    }
}
