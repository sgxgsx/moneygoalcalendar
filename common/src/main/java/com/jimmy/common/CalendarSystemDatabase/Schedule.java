package com.jimmy.common.CalendarSystemDatabase;

import java.io.Serializable;

/**
 * Created by Jimmy on 2016/10/8 0008.
 */
public class  Schedule implements Serializable {

    private int id;
    private int color;
    private String title;
    private String desc;
    private String location;
    private int state;
    private long time;
    private long time_end;
    private int year;
    private int month;
    private int day;
    private int eventSetId;

    private String repeat;
    private String account;
    private String account_name;

    public Schedule(){

    }
    public Schedule(int id, int color, String title, String desc, String location, int state, long time, long time_end, int year, String repeat, String account){
        this.id = id;
        this.color = color;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.state = state;
        this.time = time;
        this.time_end = time_end;
        this.year = year;
        this.repeat = repeat;
        this.account = account;
    }





    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
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
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location == null ? "" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getEventSetId() {
        return eventSetId;
    }

    public void setEventSetId(int eventSetId) {
        this.eventSetId = eventSetId;
    }
}
