package com.jimmy.common.GoalDatabase;

import java.io.Serializable;

public class  GoalSchedule implements Serializable{
    private final String CALENDAR_NAME = "GoalCalendar";


    private int id;
    private int color;
    private String title;
    private String desc;
    private String location;
    private boolean state;
    private long time;
    private long time_end;
    private String account;

    public GoalSchedule(int id, int color, String title, String desc, String location, boolean state, long time, long time_end, String account) {
        this.id = id;
        this.color = color;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.state = state;
        this.time = time;
        this.time_end = time_end;
        this.account = account;
    }

    public String getCALENDAR_NAME() {
        return CALENDAR_NAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime_end() {
        return time_end;
    }

    public void setTime_end(long time_end) {
        this.time_end = time_end;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
