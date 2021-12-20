package com.wil.quizzapp.controller;

import com.wil.quizzapp.aux_class.GameCurrStatus;
import com.wil.quizzapp.entities.Question;

public class GameController {

    private final int[] levelPrizeArr = {5, 10, 25, 60, 100};
    private GameCurrStatus gameStatus;

    public GameController() {
    }

    public GameCurrStatus validateAnswer(GameCurrStatus currStatus){
        Question question = currStatus.getQuestion();
        boolean isValidAns = false;
        isValidAns = question.getCorrectAnswer() == currStatus.getSelectedAnswer();
        if (isValidAns){
            return new GameCurrStatus(question, currStatus.getSelectedAnswer(),
                    currStatus.getLevel() + 1, currStatus.getScore() + levelPrizeArr[currStatus.getLevel()-1],
                    isValidAns);
        }
        return currStatus;
    }
}
