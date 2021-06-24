package com.cleanup.todoc.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM " + Project.TABLE_NAME)
    List<Project> getAllProjects();
}