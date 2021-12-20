package com.wil.quizzapp.aux_class;

import com.wil.quizzapp.entities.Question;

public class GameCurrStatus {
    private Question question;
    private int selectedAnswer;
    private int level;
    private int score;

    public GameCurrStatus(Question question, int selectedAnswer, int level, int score) {
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.level = level;
        this.score = score;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
