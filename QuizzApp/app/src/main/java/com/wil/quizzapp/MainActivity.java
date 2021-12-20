package com.wil.quizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wil.quizzapp.aux_class.GameCurrStatus;
import com.wil.quizzapp.controller.GameController;
import com.wil.quizzapp.entities.Player;
import com.wil.quizzapp.entities.Question;
import com.wil.quizzapp.view_model.PlayerViewModel;
import com.wil.quizzapp.view_model.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //View Widgets
    Button btnStart, btnGiveUp, btnSubmit;
    Toolbar toolbar;
    TextView tvLevel, tvQText, tvScore;
    RadioGroup rgAnswers;
    RadioButton rbQAns1, rbQAns2, rbQAns3, rbQAns4;
    EditText etPlayerSave;
    LinearLayout containerPlayerName;
    Chronometer chronoContdown;

    //ModelView objects
    private QuestionViewModel myQuestionViewModel;
    private PlayerViewModel myPlayerViewModel;

    // Questions cache structures
    private List<Question> level1Questions;
    private List<Question> level2Questions;
    private List<Question> level3Questions;
    private List<Question> level4Questions;
    private List<Question> level5Questions;

    // Aux variables and objects
    Question currQuestion;
    Player player;
    private int currScore = 0;
    private int currLevel = 0;
    private int selectedAnswer;
    GameCurrStatus status;
    GameController gameController;
    private int counter = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameController = new GameController();

        level1Questions = new ArrayList<Question>();
        level2Questions = new ArrayList<Question>();
        level3Questions = new ArrayList<Question>();
        level4Questions = new ArrayList<Question>();
        level5Questions = new ArrayList<Question>();

//      DataView Model Access: caching all data per level questions
        myQuestionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        myPlayerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        // Async ViewModel callbacks
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
        // Variable linking with Widgets
        tvQText = findViewById(R.id.tv_game_question_text);
        tvLevel = findViewById(R.id.tv_game_level);
        rgAnswers = findViewById(R.id.rg_answers);
        rbQAns1 = findViewById(R.id.rb_ans1);
        rbQAns2 = findViewById(R.id.rb_ans2);
        rbQAns3 = findViewById(R.id.rb_ans3);
        rbQAns4 = findViewById(R.id.rb_ans4);
        tvScore = findViewById(R.id.tv_curr_score);
        etPlayerSave = findViewById(R.id.et_player_save);
        containerPlayerName = findViewById(R.id.linlay_get_player);
        containerPlayerName.setVisibility(View.INVISIBLE);

        chronoContdown = (Chronometer) findViewById(R.id.chrono_countdown);
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
                currScore = 0;
                currQuestion = getRandomQuestion(currLevel);
                displayQuestion(currQuestion);
                chronoContdown.setText(counter + "");
                chronoContdown.start();
            }
        });

        btnGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronoContdown.setText("0");
                chronoContdown.stop();
                Toast.makeText(getApplicationContext(), "See you soon to get this done.", Toast.LENGTH_SHORT).show();
                tvScore.setText(Integer.toString(currScore));
                btnGiveUp.setVisibility(View.INVISIBLE);
                btnSubmit.setVisibility(View.INVISIBLE);
                containerPlayerName.setVisibility(View.VISIBLE);
                resetViewControlWidgets();
                resetViewQuestionWidgets();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onChronoTickHandler(true);
                chronoContdown.setText("0");
                int selectedAnswerId = rgAnswers.getCheckedRadioButtonId();
                selectedAnswer = getSelectedAnswer(selectedAnswerId);

                if (selectedAnswerId != -1){
                    status = gameController.validateAnswer(new GameCurrStatus(currQuestion,
                            selectedAnswer, currLevel, currScore, false));
                    if (status.isValidated()) {
                        currLevel = status.getLevel();
                        currScore = status.getScore();
                        if (currLevel < 6){
                            status.setSelectedAnswer(0);
                            currQuestion = getRandomQuestion(currLevel);
                            Toast.makeText(getApplicationContext(), "Well Done! Moving forward to next level.", Toast.LENGTH_SHORT).show();
                            displayQuestion(currQuestion);
                            chronoContdown.start();
                        }else {
                            Toast.makeText(getApplicationContext(), "Congrats! You beat it!!", Toast.LENGTH_SHORT).show();
                            tvScore.setText(Integer.toString(currScore));
                            btnGiveUp.setVisibility(View.INVISIBLE);
                            btnSubmit.setVisibility(View.INVISIBLE);
                            containerPlayerName.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Keep trying... :(", Toast.LENGTH_SHORT).show();
                        resetViewControlWidgets();
                        resetViewQuestionWidgets();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.select_ans_warning, Toast.LENGTH_SHORT).show();
                }
            }

            void onClickSaveName(View view){

            }
        });

        chronoContdown.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                onChronoTickHandler(false);
            }
        });

    }

    private void onChronoTickHandler(boolean submitClicked) {
        if (counter <= 0){
            counter = 15;
            chronoContdown.stop();
            chronoContdown.setText("0");
            Toast.makeText(getApplicationContext(), "Time is up...Keep trying... :(", Toast.LENGTH_SHORT).show();
            resetViewControlWidgets();
            resetViewQuestionWidgets();
        }else if(submitClicked) {
            counter = 15;
            chronoContdown.stop();
            chronoContdown.setText("0");
        }else {
            chronoContdown.setText(counter + "");
            counter --;
        }
    }

    private void resetViewControlWidgets() {
//      View components state update
        btnGiveUp.setVisibility(View.INVISIBLE);
        btnSubmit.setVisibility(View.INVISIBLE);
        btnStart.setEnabled(true);
        getSupportActionBar().show();
    }

    private int getSelectedAnswer(int selectedAnswerId) {
        int selAnswer = 0;
        if (selectedAnswerId == rbQAns1.getId()) {
            selAnswer = 1;
        } else if (selectedAnswerId == rbQAns2.getId()) {
            selAnswer = 2;
        } else if (selectedAnswerId == rbQAns3.getId()) {
            selAnswer = 3;
        }else if (selectedAnswerId == rbQAns4.getId()) {
            selAnswer = 4;
        }
        return selAnswer;
    }

    private void displayQuestion(Question levelQuestion) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        StringBuilder sb5 = new StringBuilder();
        StringBuilder sb6 = new StringBuilder();
        rgAnswers.clearCheck();

        tvLevel.setText(sb5.append("Level ").append(currLevel).append(" question"));
        tvQText.setText(levelQuestion.getText());
        rbQAns1.setText(sb1.append("1. ").append(levelQuestion.getAnswer1()));
        rbQAns2.setText(sb2.append("2. ").append(levelQuestion.getAnswer2()));
        rbQAns3.setText(sb3.append("3. ").append(levelQuestion.getAnswer3()));
        rbQAns4.setText(sb4.append("4. ").append(levelQuestion.getAnswer4()));
        tvScore.setText(sb6.append(currScore).append("/200"));
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

    private Question getRandomQuestion(int currLevel) {
        List<Question> questions;
        questions = getQuestionList(currLevel);
        Random rand = new Random();
        return questions.get(rand.nextInt(questions.size()));
    }

    private List<Question> getQuestionList(int currLevel) {
        switch (currLevel){
            case 1:
                return level1Questions;
            case 2:
                return level2Questions;
            case 3:
                return level3Questions;
            case 4:
                return level4Questions;
            case 5:
                return level5Questions;
        }
        return null;
    }

    public void onClickSaveName(View view) {
        if (TextUtils.isEmpty(etPlayerSave.getText())) {
            Toast.makeText(getApplicationContext(), R.string.player_save_warning, Toast.LENGTH_SHORT).show();
        }else{
            player = new Player(etPlayerSave.getText().toString(), currScore, Integer.toString(5));
            myPlayerViewModel.insertPlayer(player);
            etPlayerSave.setText("");
            containerPlayerName.setVisibility(View.INVISIBLE);
            resetViewControlWidgets();
            resetViewQuestionWidgets();
        }
    }

    private void resetViewQuestionWidgets() {
        tvLevel.setText("Level # question");
        tvQText.setText("What is the question?");
        rbQAns1.setText("Possible Answer 1");
        rbQAns2.setText("Possible Answer 2");
        rbQAns3.setText("Possible Answer 3");
        rbQAns4.setText("Possible Answer 4");
        tvScore.setText("0/200");
    }

    public void onCLickCancelSaveName(View view) {
        resetViewControlWidgets();
        resetViewQuestionWidgets();
        containerPlayerName.setVisibility(View.INVISIBLE);
    }
}