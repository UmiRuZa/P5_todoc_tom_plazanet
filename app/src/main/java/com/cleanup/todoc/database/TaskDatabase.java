package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities =
        {Task.class, Project.class},
        version = 5,
        exportSchema = false
)
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase database;

    private static String DATABASE_NAME = "TODOC_database";

    public synchronized static TaskDatabase getInstance(Context context){
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(CALLBACK)
                    .build();
        }
        return database;
    }


    public static Callback CALLBACK = new Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            initialiseProjects(db);
        }

        @Override
        public void onOpen(SupportSQLiteDatabase db) {
            super.onOpen(db);
            Cursor csr = db.query("SELECT count(*) FROM " + Project.TABLE_NAME);
            if (csr.moveToFirst()) {
                long row_count = csr.getLong(0);
                if (row_count != Project.DEFINED_PROJECTS.length) initialiseProjects(db);
            }
            csr.close();
        }
    };

    private static void initialiseProjects(SupportSQLiteDatabase db) {
        boolean alreadyInTransaction = db.inTransaction();
        ContentValues cv = new ContentValues();
        if (!alreadyInTransaction) db.beginTransaction();
        db.delete(Project.TABLE_NAME,null,null);
        for(Project p: Project.DEFINED_PROJECTS) {
            cv.clear();
            cv.put(Project.ID_COLUMN_NAME,p.getProjectId());
            cv.put(Project.NAME_COLUMN_NAME,p.getName());
            cv.put(Project.COLOR_COLUMN_NAME,p.getColor());
            db.insert(Project.TABLE_NAME, OnConflictStrategy.IGNORE,cv);
        }
        if (!alreadyInTransaction) {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();
}
