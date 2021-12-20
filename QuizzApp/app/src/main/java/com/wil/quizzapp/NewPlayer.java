package com.wil.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NewPlayer extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.wil.quizzapp.quizzappsql.REPLY";

    EditText playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);

        playerName = findViewById(R.id.et_player_name);

        final Button btn_add_player = findViewById(R.id.btn_add_player);
        btn_add_player.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(playerName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String name = playerName.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, name);
                setResult(RESULT_OK, replyIntent);
            }
            finish();

        });
    }
}