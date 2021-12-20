package com.wil.quizzapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

        PlayerViewModel myPlayerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PlayerListAdapter adapter = new PlayerListAdapter(new PlayerListAdapter.PlayerDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myPlayerViewModel.getAllPlayers().observe(this, players -> {
            adapter.submitList(players);
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_player);
        fab.setOnClickListener( view -> {
            Intent newPlayerIntent = new Intent(ResultHistoryActivity.this, NewPlayer.class);
            startActivityForResult(newPlayerIntent, NEW_PLAYER_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PLAYER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(
                    getApplicationContext(),
                    data.getStringExtra(NewPlayer.EXTRA_REPLY),
                    Toast.LENGTH_LONG).show();
            Player player = new Player(data.getStringExtra(NewPlayer.EXTRA_REPLY));

            myPlayerViewModel.insertPlayer(player);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Player not saved",
                    Toast.LENGTH_LONG).show();
        }
    }

}