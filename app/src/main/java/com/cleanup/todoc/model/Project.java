package com.cleanup.todoc.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = Project.TABLE_NAME)
public class Project {

    public static final Project[] DEFINED_PROJECTS = {
            new Project(1L, "Projet Tartampion", 0xFFEADAD1),
            new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
            new Project(3L, "Projet Circus", 0xFFA3CED2)
    };

    public static final String TABLE_NAME = "project";
    public static final String ID_COLUMN_NAME = "p_projectId";
    public static final String NAME_COLUMN_NAME = "name";
    public static final String COLOR_COLUMN_NAME = "color";

    @PrimaryKey
    @ColumnInfo(name = ID_COLUMN_NAME)
    private long projectId;

    @NonNull
    @ColumnInfo(name = NAME_COLUMN_NAME)
    private String name;

    @ColorInt
    @ColumnInfo(name = COLOR_COLUMN_NAME)
    private int color;

    private Project(long projectId, @NonNull String name, @ColorInt int color) {
        this.projectId = projectId;
        this.name = name;
        this.color = color;
    }

    public Project(){}

    @Nullable
    public static Project getProjectById(long id) {
        for (Project project : DEFINED_PROJECTS) {
            if (project.projectId == id)
                return project;
        }
        return null;
    }

    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Returns the hex (ARGB) code of the color associated to the project.
     *
     * @return the hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
