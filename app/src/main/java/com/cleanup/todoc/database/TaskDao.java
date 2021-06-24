package com.cleanup.todoc.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task_table")
    List<Task> getAll();

    @Insert(onConflict = REPLACE)
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(List<Task> tasks);
}
