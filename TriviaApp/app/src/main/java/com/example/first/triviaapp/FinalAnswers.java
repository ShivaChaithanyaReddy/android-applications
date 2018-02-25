package com.example.first.triviaapp;

import java.io.Serializable;

/**
 * Created by Chaithanya on 2/9/2017.
 */

public class FinalAnswers implements Serializable{

    String question;
    String correctAnswer;
    String givenAnswer;

    @Override
    public String toString() {
        return "FinalAnswers{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", givenAnswer='" + givenAnswer + '\'' +
                '}';
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}
