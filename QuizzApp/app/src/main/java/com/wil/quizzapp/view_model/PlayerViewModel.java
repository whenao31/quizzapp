package com.wil.quizzapp.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wil.quizzapp.entities.Player;
import com.wil.quizzapp.repository.PlayerRepo;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepo myPlayerRepo;

    private final LiveData<List<Player>> myAllPlayers;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        myPlayerRepo = new PlayerRepo(application);
        myAllPlayers = myPlayerRepo.getAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers() { return myAllPlayers; }

    public void insertPlayer(Player player) { myPlayerRepo.insertPlayer(player); }
}
