package com.wil.quizzapp.room_db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wil.quizzapp.daos.PlayerDao;
import com.wil.quizzapp.daos.QuestionDao;
import com.wil.quizzapp.entities.Player;
import com.wil.quizzapp.entities.Question;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class, Question.class}, version = 1, exportSchema = false)
public abstract class QuizzAppDb extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract QuestionDao questionDao();

    private static volatile QuizzAppDb INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

//    Singleton to prevent having multiple instances of the database opened at the same time.
    public static QuizzAppDb getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (QuizzAppDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuizzAppDb.class,
                            "quizz_app_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
