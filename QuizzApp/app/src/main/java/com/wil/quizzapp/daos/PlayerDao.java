package com.wil.quizzapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wil.quizzapp.entities.Player;

import java.util.List;
@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlayer(Player player);

    @Query("SELECT * FROM player_table ORDER BY score DESC")
    LiveData<List<Player>> getScoreOrderedPlayers();
//    List<Player> getScoreOrderedPlayers();

    @Query("SELECT * FROM player_table WHERE name LIKE :playerName LIMIT 1")
    Player findPlayerByName(String playerName);

    @Update
    void updatePlayer(Player player);

    @Delete
    void deletePlayer(Player player);
}
