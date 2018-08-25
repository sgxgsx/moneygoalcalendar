package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.jimmy.common.SettingsDatabase.CalendarSettingsDao;

@Database(entities = {Goal.class}, version = 1, exportSchema = false)
@TypeConverters({GoalScheduleConverter.class, AimConverter.class})
public abstract class GoalDatabase extends RoomDatabase {
    private static final String LOG_TAG = GoalDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "GoalDatabaseRooom";
    private static GoalDatabase GoalDatabase;

    public static GoalDatabase getInstance(Context context){
        if (GoalDatabase == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database");
                GoalDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        GoalDatabase.class, DATABASE_NAME).build();
            }
        }
        Log.d(LOG_TAG, "getting database instance");
        return GoalDatabase;
    }

    public abstract GoalDao goalDao();
}
