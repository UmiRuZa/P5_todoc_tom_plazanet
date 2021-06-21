package com.cleanup.todoc.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class}, version = 4, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase database;

    private static String DATABASE_NAME = "TODOC_database";

    public synchronized static TaskDatabase getInstance(Context context){
     if (database == null) {
         database = Room.databaseBuilder(context.getApplicationContext(),
                 TaskDatabase.class, DATABASE_NAME)
                 .allowMainThreadQueries()
                 .fallbackToDestructiveMigration()
                 .build();
     }
     return database;
    }

    public abstract TaskDao taskDao();
}
