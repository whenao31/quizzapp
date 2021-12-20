package com.wil.quizzapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.wil.quizzapp.daos.QuestionDao;
import com.wil.quizzapp.entities.Question;
import com.wil.quizzapp.room_db.QuizzAppDb;

import java.util.List;

public class QuestionRepo {

    private QuestionDao myQuestionDao;
    private LiveData<List<Question>> myAllQuestions;
    private LiveData<List<Question>> myLevel1Questions;
    private LiveData<List<Question>> myLevel2Questions;
    private LiveData<List<Question>> myLevel3Questions;
    private LiveData<List<Question>> myLevel4Questions;
    private LiveData<List<Question>> myLevel5Questions;

    public QuestionRepo(Application application) {
        QuizzAppDb db = QuizzAppDb.getDatabase(application);
        myQuestionDao = db.questionDao();
        myAllQuestions = myQuestionDao.getAllQuestions();
        myLevel1Questions = myQuestionDao.getLevelQuestions(1);
        myLevel2Questions = myQuestionDao.getLevelQuestions(2);
        myLevel3Questions = myQuestionDao.getLevelQuestions(3);
        myLevel4Questions = myQuestionDao.getLevelQuestions(4);
        myLevel5Questions = myQuestionDao.getLevelQuestions(5);
    }

    public LiveData<List<Question>> getAllQuestions() { return myAllQuestions; }

    public LiveData<List<Question>> getLevel1Questions() {return myLevel1Questions;}
    public LiveData<List<Question>> getLevel2Questions() {return myLevel2Questions;}
    public LiveData<List<Question>> getLevel3Questions() {return myLevel3Questions;}
    public LiveData<List<Question>> getLevel4Questions() {return myLevel4Questions;}
    public LiveData<List<Question>> getLevel5Questions() {return myLevel5Questions;}

    public void insertQuestion(Question question) {
        QuizzAppDb.databaseWriterExecutor.execute(() -> {
            myQuestionDao.insertQuestion(question);
        });
    }

//    public LiveData<List<Question>> getLevelQuestions(int level) {
//        QuizzAppDb.databaseWriterExecutor.execute(() -> {
//            myLevelQuestions = myQuestionDao.getLevelQuestions(level);
//        });
//        return myLevelQuestions;
//    }
}
