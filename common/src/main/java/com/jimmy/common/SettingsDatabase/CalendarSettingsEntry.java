package com.jimmy.common.SettingsDatabase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="CalendarSettingsTable")
public class  CalendarSettingsEntry {
    @PrimaryKey
    private int calendarID;
    private String calendarName;
    private boolean show;

    public CalendarSettingsEntry(int calendarID, String calendarName, boolean show) {
        this.calendarID = calendarID;
        this.calendarName = calendarName;
        this.show = show;
    }

    public int getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(int calendarID) {
        this.calendarID = calendarID;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
