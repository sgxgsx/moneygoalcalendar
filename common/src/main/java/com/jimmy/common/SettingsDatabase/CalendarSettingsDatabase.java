package com.jimmy.common.SettingsDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;
import android.widget.ScrollView;


@Database(entities = {CalendarSettingsEntry.class}, version = 1, exportSchema = false)
public abstract class  CalendarSettingsDatabase extends RoomDatabase {
    private static final String LOG_TAG = CalendarSettingsDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "CalendarSettingsDatabase";
    private static CalendarSettingsDatabase calendarSettingsDatabase;

    public static CalendarSettingsDatabase getInstance(Context context){
        if (calendarSettingsDatabase == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database");
                calendarSettingsDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        CalendarSettingsDatabase.class, CalendarSettingsDatabase.DATABASE_NAME).build();
            }
        }
        Log.d(LOG_TAG, "getting database instance");
        return calendarSettingsDatabase;
    }

    public abstract CalendarSettingsDao calendarSettingsDao();
}
