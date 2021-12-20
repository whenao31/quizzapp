package com.wil.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewQuestionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REPLY = "com.wil.quizzapp.quizzappsql.REPLY";
    private final String[] questionAnsNumberArr = {"1", "2", "3", "4"};
    private final String[] questionLevelArr = {"1", "2", "3", "4", "5"};

    EditText questionText;
    EditText questionAnswer1;
    EditText questionAnswer2;
    EditText questionAnswer3;
    EditText questionAnswer4;
    Spinner spinQuestionCorrectAns;
    Spinner spinQuestionLevel;
    Button btnSaveQuestion;

    String selectedCorrectAnsStr;
    String selectedLevelStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        questionText = findViewById(R.id.et_newq_question_text);
        questionAnswer1 = findViewById(R.id.et_newq_answer1);
        questionAnswer2 = findViewById(R.id.et_newq_answer2);
        questionAnswer3 = findViewById(R.id.et_newq_answer3);
        questionAnswer4 = findViewById(R.id.et_newq_answer4);

        spinQuestionCorrectAns = (Spinner)findViewById(R.id.spinner_correct_ans);
        spinQuestionLevel = (Spinner)findViewById(R.id.spinner_q_level);

        // Spinners settings
        spinQuestionCorrectAns.setOnItemSelectedListener(this);
        spinQuestionLevel.setOnItemSelectedListener(this);

        ArrayAdapter answerNumAA = new ArrayAdapter(this, android.R.layout.simple_spinner_item, questionAnsNumberArr);
        answerNumAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinQuestionCorrectAns.setAdapter(answerNumAA);

        ArrayAdapter qLevelAA = new ArrayAdapter(this, android.R.layout.simple_spinner_item, questionLevelArr);
        qLevelAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinQuestionLevel.setAdapter(qLevelAA);

        // Save question button handling
        btnSaveQuestion = findViewById(R.id.btn_save_question);
        btnSaveQuestion.setOnClickListener(view -> {

            if (!dataValidation()) {
//                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(this, "Please validate all the data", Toast.LENGTH_SHORT).show();
            } else {
                Intent replyIntent = new Intent();
                replyIntent.putExtras(getDataBundle());
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        if (spinner.getId() == R.id.spinner_correct_ans){
            selectedCorrectAnsStr = questionAnsNumberArr[position];
            Toast.makeText(getApplicationContext(), selectedCorrectAnsStr, Toast.LENGTH_SHORT).show();
        }else if(spinner.getId() == R.id.spinner_q_level){
            selectedLevelStr = questionLevelArr[position];
            Toast.makeText(getApplicationContext(), selectedLevelStr, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean dataValidation() {
        if ( TextUtils.isEmpty(questionText.getText()) || TextUtils.isEmpty(questionAnswer1.getText()) ||
                TextUtils.isEmpty(questionAnswer2.getText()) || TextUtils.isEmpty(questionAnswer3.getText()) ||
                TextUtils.isEmpty(questionAnswer4.getText()) || !Integer.toString(Integer.parseInt(selectedLevelStr)).matches("[1-5].*") ||
                !Integer.toString(Integer.parseInt(selectedCorrectAnsStr)).matches("[1-4].*") ) {
            return false;
        }
        return true;
    }

    private Bundle getDataBundle() {
        Bundle data = new Bundle();
        data.putString("qText", questionText.getText().toString());
        data.putString("qAns1", questionAnswer1.getText().toString());
        data.putString("qAns2", questionAnswer2.getText().toString());
        data.putString("qAns3", questionAnswer3.getText().toString());
        data.putString("qAns4", questionAnswer4.getText().toString());
        data.putInt("qCorrectAns", Integer.parseInt(selectedCorrectAnsStr));
        data.putInt("qLevel", Integer.parseInt(selectedLevelStr));
        return data;
    }
}