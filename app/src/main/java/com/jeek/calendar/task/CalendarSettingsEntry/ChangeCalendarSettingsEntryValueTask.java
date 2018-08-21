package com.jeek.calendar.task.CalendarSettingsEntry;

import android.content.Context;
import android.os.AsyncTask;

import com.jimmy.common.SettingsDatabase.CalendarSettingsDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;
import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.List;

public class ChangeCalendarSettingsEntryValueTask extends AsyncTask<CalendarSettingsEntry, Void, Void> {

    protected Context mContext;
    private CalendarSettingsEntry calendarSettingsEntry;

    public ChangeCalendarSettingsEntryValueTask(Context context, CalendarSettingsEntry caalendarSettingsEntry) {
        mContext = context;
        calendarSettingsEntry = caalendarSettingsEntry;
        caalendarSettingsEntry.setShow(!caalendarSettingsEntry.isShow());
    }

    @Override
    protected Void doInBackground(CalendarSettingsEntry... calendarSettingsEntries) {
        CalendarSettingsDatabase.getInstance(mContext).calendarSettingsDao().updateSettingsCalendar(calendarSettingsEntry);
        return null;
    }
}