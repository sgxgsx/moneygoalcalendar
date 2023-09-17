package com.jeek.calendar.task.CalendarSettingsEntry;

import android.content.Context;

import com.jimmy.common.CalendarSystemDatabase.CalendarClass;
import com.jimmy.common.CalendarSystemDatabase.CalendarClassDao;
import com.jimmy.common.SettingsDatabase.CalendarSettingsDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;
import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.List;

public class LoadAllCalendarSettingsEntryTask extends BaseAsyncTask<List<CalendarSettingsEntry>> {
    private Context mContext;

    public LoadAllCalendarSettingsEntryTask(Context context, OnTaskFinishedListener<List<CalendarSettingsEntry>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected List<CalendarSettingsEntry> doInBackground(Void... params) {
        return CalendarSettingsDatabase.getInstance(mContext).calendarSettingsDao().loadSettingsCalendars();
    }
}