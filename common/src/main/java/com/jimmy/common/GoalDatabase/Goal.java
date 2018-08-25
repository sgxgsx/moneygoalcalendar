package com.jimmy.common.GoalDatabase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.jimmy.common.CalendarSystemDatabase.Schedule;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "GoalsTable")
public class Goal implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String goal_name;
    private long date_to;
    private List<Aim> aims;
    private List<GoalSchedule> schedules;


    @Ignore
    public Goal(String goal_name, long date_to, List<Aim> aims, List<GoalSchedule> schedules) {
        this.goal_name = goal_name;
        this.date_to = date_to;
        this.aims = aims;
        this.schedules = schedules;
    }

    public Goal(int id, String goal_name, long date_to, List<Aim> aims, List<GoalSchedule> schedules) {
        this.id = id;
        this.goal_name = goal_name;
        this.date_to = date_to;
        this.aims = aims;
        this.schedules = schedules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public long getDate_to() {
        return date_to;
    }

    public void setDate_to(long date_to) {
        this.date_to = date_to;
    }

    public List<Aim> getAims() {
        return aims;
    }

    public void setAims(List<Aim> aims) {
        this.aims = aims;
    }

    public List<GoalSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<GoalSchedule> schedules) {
        this.schedules = schedules;
    }
}
