package com.wil.quizzapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.wil.quizzapp.daos.PlayerDao;
import com.wil.quizzapp.entities.Player;
import com.wil.quizzapp.room_db.QuizzAppDb;

import java.util.List;

public class PlayerRepo {

    private PlayerDao myPlayerDao;
    private LiveData<List<Player>> myAllPlayers;

    public PlayerRepo(Application application) {
        QuizzAppDb db = QuizzAppDb.getDatabase(application);
        myPlayerDao = db.playerDao();
        myAllPlayers = myPlayerDao.getScoreOrderedPlayers();
    }

    public LiveData<List<Player>> getAllPlayers() {
        return myAllPlayers;
    }

    public void insertPlayer(Player player) {
        QuizzAppDb.databaseWriterExecutor.execute(() -> {
            myPlayerDao.insertPlayer(player);
        });
    }
}
