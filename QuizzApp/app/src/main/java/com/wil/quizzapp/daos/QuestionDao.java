package com.wil.quizzapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wil.quizzapp.entities.Question;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertQuestion(Question question);

    @Query("SELECT * FROM question_table ORDER BY level")
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT * FROM question_table WHERE id = :questionId")
    Question findQuestionById(int questionId);

    @Query("SELECT * FROM question_table WHERE level = :level")
    LiveData<List<Question>> getLevelQuestions(int level);

    @Update
    void updateQuestion(Question question);

    @Delete
    void deleteQuestion(Question question);
}
