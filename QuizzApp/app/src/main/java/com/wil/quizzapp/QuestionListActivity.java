package com.wil.quizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wil.quizzapp.entities.Player;
import com.wil.quizzapp.entities.Question;
import com.wil.quizzapp.view_model.QuestionViewModel;

public class QuestionListActivity extends AppCompatActivity {

    private QuestionViewModel myQuestionViewModel;

    public static final int NEW_QUESTION_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_question);
        setSupportActionBar(toolbar);

        myQuestionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        RecyclerView questionRecView = findViewById(R.id.recyclerview_question);
        final QuestionListAdapter questionAdapter = new QuestionListAdapter(new QuestionListAdapter.QuestionDiff());
        questionRecView.setAdapter(questionAdapter);
        questionRecView.setLayoutManager(new LinearLayoutManager(this));

//        myQuestionViewModel.getAllQuestions().observe(this, questions -> {
//            questionAdapter.submitList(questions);
//        });
        myQuestionViewModel.getLevel1Questions().observe(this, questions -> {
            questionAdapter.submitList(questions);
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_question);
        fab.setOnClickListener(view -> {
            Intent newQuestionIntent = new Intent(QuestionListActivity.this, NewQuestionActivity.class);
            startActivityForResult(newQuestionIntent, NEW_QUESTION_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_QUESTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Toast.makeText(
//                    getApplicationContext(),
//                    data.getStringExtra(NewQuestionActivity.EXTRA_REPLY),
//                    Toast.LENGTH_LONG).show();
            Question question = new Question(data.getStringExtra("qText"),
                                            data.getIntExtra("qCorrectAns", 0),
                                            data.getStringExtra("qAns1"),
                                            data.getStringExtra("qAns2"),
                                            data.getStringExtra("qAns3"),
                                            data.getStringExtra("qAns4"),
                                            data.getIntExtra("qLevel", 0));

            myQuestionViewModel.insertQuestion(question);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Question not saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_activity:
                Intent intMain = new Intent(this, MainActivity.class);
                startActivity(intMain);
                break;
            case R.id.view_history:
                Intent intHist = new Intent(this, ResultHistoryActivity.class);
                startActivity(intHist);
                break;
        }
        return true;
    }
}