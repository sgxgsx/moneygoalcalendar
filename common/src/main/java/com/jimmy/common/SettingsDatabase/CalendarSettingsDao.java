package com.jimmy.common.SettingsDatabase;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CalendarSettingsDao {
    @Query("SELECT * FROM CalendarSettingsTable ORDER BY calendarID")
    List<CalendarSettingsEntry> loadSettingsCalendars();

    @Query("SELECT * FROM CalendarSettingsTable ORDER BY calendarID")
    LiveData<List<CalendarSettingsEntry>> loadLiveDataSettingsCalendars();

    @Insert
    void insertSettingsCalendar(CalendarSettingsEntry calendarSettingsEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSettingsCalendar(CalendarSettingsEntry calendarSettingsEntry);

    @Delete
    void deleteSettingsCalendar(CalendarSettingsEntry calendarSettingsEntry);
}
