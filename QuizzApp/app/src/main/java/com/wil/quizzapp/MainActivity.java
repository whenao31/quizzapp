package com.wil.quizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wil.quizzapp.entities.Question;
import com.wil.quizzapp.view_model.QuestionViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnGiveUp;
    Button btnSubmit;
    Toolbar toolbar;
    TextView tvlevel;
    TextView tvQText;
    RadioButton rbQAns1;
    RadioButton rbQAns2;
    RadioButton rbQAns3;
    RadioButton rbQAns4;

    private QuestionViewModel myQuestionViewModel;
    private List<Question> level1Questions;
    private List<Question> level2Questions;
    private List<Question> level3Questions;
    private List<Question> level4Questions;
    private List<Question> level5Questions;
    HashMap<Integer, List<Question>> questionsMap;

    private final int[] prizeArr = {1, 5, 20, 60, 100};
    private int currScore = 0;
    private int currLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        level1Questions = new ArrayList<Question>();
        level2Questions = new ArrayList<Question>();
        level3Questions = new ArrayList<Question>();
        level4Questions = new ArrayList<Question>();
        level5Questions = new ArrayList<Question>();

        myQuestionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        myQuestionViewModel.getLevel1Questions().observe(this, questions -> {
            level1Questions = questions;
        });
        myQuestionViewModel.getLevel2Questions().observe(this, questions -> {
            level2Questions = questions;
        });
        myQuestionViewModel.getLevel3Questions().observe(this, questions -> {
            level3Questions = questions;
        });
        myQuestionViewModel.getLevel4Questions().observe(this, questions -> {
            level4Questions = questions;
        });
        myQuestionViewModel.getLevel5Questions().observe(this, questions -> {
            level5Questions = questions;
        });

        tvQText = findViewById(R.id.tv_game_question_text);
        tvlevel = findViewById(R.id.tv_game_level);
        rbQAns1 = findViewById(R.id.rb_ans1);
        rbQAns2 = findViewById(R.id.rb_ans2);
        rbQAns3 = findViewById(R.id.rb_ans3);
        rbQAns4 = findViewById(R.id.rb_ans4);

        btnStart = findViewById(R.id.btn_start_game);
        btnGiveUp = findViewById(R.id.btn_giveup_game);
        btnSubmit = findViewById(R.id.btn_submit_answer);
        btnGiveUp.setVisibility(View.INVISIBLE);
        btnSubmit.setVisibility(View.INVISIBLE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGiveUp.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                btnStart.setEnabled(false);
                getSupportActionBar().hide();

                currLevel = 1;
                displayQuestion(getRandomQuestion(level1Questions));
            }
        });



        btnGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGiveUp.setVisibility(View.INVISIBLE);
                btnSubmit.setVisibility(View.INVISIBLE);
                btnStart.setEnabled(true);
                getSupportActionBar().show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGiveUp.setVisibility(View.INVISIBLE);
                btnSubmit.setVisibility(View.INVISIBLE);
                btnStart.setEnabled(true);
                getSupportActionBar().show();
            }
        });

    }

    private void displayQuestion(Question levelQuestion) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        StringBuilder sb5 = new StringBuilder();

        tvlevel.setText(sb5.append("Level ").append(currLevel).append(" question"));
        tvQText.setText(levelQuestion.getText());
        rbQAns1.setText(sb1.append("1. ").append(levelQuestion.getAnswer1()));
        rbQAns2.setText(sb2.append("2. ").append(levelQuestion.getAnswer2()));
        rbQAns3.setText(sb3.append("3. ").append(levelQuestion.getAnswer3()));
        rbQAns4.setText(sb4.append("4. ").append(levelQuestion.getAnswer4()));
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
            case R.id.add_question:
                Intent intQuestion = new Intent(this, QuestionListActivity.class);
                startActivity(intQuestion);
                break;
            case R.id.view_history:
                Intent intHist = new Intent(this, ResultHistoryActivity.class);
                startActivity(intHist);
                break;
        }
        return true;
    }

    private Question getRandomQuestion(List<Question> questions) {
        Random rand = new Random();
        return questions.get(rand.nextInt(questions.size()));
    }

}