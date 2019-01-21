package com.jimmy.common.GoalDatabase;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;

import java.util.List;

@Dao
public interface  GoalDao {
    @Query("SELECT * FROM GoalsTable ORDER BY id")
    LiveData<List<Goal>> loadGoals();

    @Query("SELECT * FROM GoalsTable WHERE done=1 ORDER BY id")
    LiveData<List<Goal>> loadDoingGoals();

    @Query("SELECT * FROM GoalsTable WHERE done=0 ORDER BY id")
    LiveData<List<Goal>> loadDoneGoals();

    @Query("SELECT * FROM GoalsTable WHERE done=0 AND done=1")
    LiveData<List<Goal>> loadNothing();

    @Insert
    void insertGoal(Goal goal);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSettingsCalendar(Goal goal);

    @Delete
    void deleteSettingsCalendar(Goal goal);
}
