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
import com.wil.quizzapp.view_model.PlayerViewModel;

public class ResultHistoryActivity extends AppCompatActivity {

    private PlayerViewModel myPlayerViewModel;

    public static final int NEW_PLAYER_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);

        myPlayerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PlayerListAdapter adapter = new PlayerListAdapter(new PlayerListAdapter.PlayerDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myPlayerViewModel.getAllPlayers().observe(this, players -> {
            adapter.submitList(players);
        });
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
            case R.id.add_question:
                Intent intQuest = new Intent(this, QuestionListActivity.class);
                startActivity(intQuest);
                break;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PLAYER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(
                    getApplicationContext(),
                    data.getStringExtra(NewPlayer.EXTRA_REPLY),
                    Toast.LENGTH_LONG).show();
            Player player = new Player(data.getStringExtra(NewPlayer.EXTRA_REPLY), 0, "0");

            myPlayerViewModel.insertPlayer(player);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Player not saved",
                    Toast.LENGTH_LONG).show();
        }
    }

}