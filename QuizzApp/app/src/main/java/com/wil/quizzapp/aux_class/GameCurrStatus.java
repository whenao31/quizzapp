package com.wil.quizzapp.aux_class;

import com.wil.quizzapp.entities.Question;

public class GameCurrStatus {
    private Question question;
    private int selectedAnswer;
    private int level;
    private int score;
    private boolean validated;

    public GameCurrStatus(Question question, int selectedAnswer, int level, int score, boolean validated) {
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.level = level;
        this.score = score;
        this.validated = validated;
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

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
