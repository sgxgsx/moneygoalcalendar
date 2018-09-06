package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.jimmy.common.CalendarSystemDatabase.Schedule;

import java.io.Serializable;
import java.util.List;

public class Aim implements Serializable{
    @PrimaryKey(autoGenerate = true)
    int id;
    int doneschedules;
    int inprogress;
    String name;
    boolean done;
    List<GoalSchedule> scheduleList;

    @Ignore
    public Aim(String name, boolean done, List<GoalSchedule> scheduleList){
        this.name = name;
        this.doneschedules = 0;
        this.inprogress = 0;
        this.done = done;
        this.scheduleList = scheduleList;
    }

    public Aim(int id, String name, boolean done, List<GoalSchedule> scheduleList) {
        this.id = id;
        this.name = name;
        this.doneschedules = 0;
        this.inprogress = 0;
        this.done = done;
        this.scheduleList = scheduleList;
    }

    public int getDoneschedules() {
        return doneschedules;
    }

    public void setDoneschedules(int doneschedules) {
        this.doneschedules = doneschedules;
    }

    public int getInprogress() {
        return inprogress;
    }

    public void setInprogress(int inprogress) {
        this.inprogress = inprogress;
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
