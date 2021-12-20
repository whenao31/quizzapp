package com.wil.quizzapp.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wil.quizzapp.entities.Question;
import com.wil.quizzapp.repository.QuestionRepo;

import java.util.List;

public class QuestionViewModel  extends AndroidViewModel {

    private QuestionRepo myQuestionRepo;
    private final LiveData<List<Question>> myAllQuestions;
    private LiveData<List<Question>> myLevel1Questions;
    private LiveData<List<Question>> myLevel2Questions;
    private LiveData<List<Question>> myLevel3Questions;
    private LiveData<List<Question>> myLevel4Questions;
    private LiveData<List<Question>> myLevel5Questions;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        myQuestionRepo = new QuestionRepo(application);
        myAllQuestions = myQuestionRepo.getAllQuestions();
        myLevel1Questions = myQuestionRepo.getLevel1Questions();
        myLevel2Questions = myQuestionRepo.getLevel2Questions();
        myLevel3Questions = myQuestionRepo.getLevel3Questions();
        myLevel4Questions = myQuestionRepo.getLevel4Questions();
        myLevel5Questions = myQuestionRepo.getLevel5Questions();
    }

    public LiveData<List<Question>> getAllQuestions() { return myAllQuestions;}
    public LiveData<List<Question>> getLevel1Questions() { return myLevel1Questions;}
    public LiveData<List<Question>> getLevel2Questions() { return myLevel2Questions;}
    public LiveData<List<Question>> getLevel3Questions() { return myLevel3Questions;}
    public LiveData<List<Question>> getLevel4Questions() { return myLevel4Questions;}
    public LiveData<List<Question>> getLevel5Questions() { return myLevel5Questions;}

    public void insertQuestion(Question question) { myQuestionRepo.insertQuestion(question); }

//    public LiveData<List<Question>> getLevelQuestions(int level) {
//        return myQuestionRepo.getLevelQuestions(level);
//    }
}
