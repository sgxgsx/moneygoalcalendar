package com.jimmy.common.GoalDatabase;

import com.jimmy.common.CalendarSystemDatabase.Schedule;

import java.io.Serializable;
import java.util.List;

public class Aim implements Serializable{
    int id;
    String name;
    boolean done;
    List<GoalSchedule> scheduleList;

    public Aim(int id, String name, boolean done, List<GoalSchedule> scheduleList) {
        this.id = id;
        this.name = name;
        this.done = done;
        this.scheduleList = scheduleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<GoalSchedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<GoalSchedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
