package com.jeek.calendar.task.CalendarSettingsEntry;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jimmy.common.CalendarSystemDatabase.CalendarClass;
import com.jimmy.common.CalendarSystemDatabase.CalendarClassDao;
import com.jimmy.common.SettingsDatabase.CalendarSettingsDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;
import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.listener.OnTaskFinishedListener;

public class AddCalendarTask extends BaseAsyncTask<Integer> {
    private int cid;

    public AddCalendarTask(Context context, OnTaskFinishedListener onTaskFinishedListener, Integer id) {
        super(context,onTaskFinishedListener);
        cid = id;
    }

    @Override
    protected Integer doInBackground(Void... integers) {
        boolean bool = CalendarSettingsDatabase.getInstance(mContext).calendarSettingsDao().ifOurCalendarIsInDb(cid);
        if(!bool){
            Log.wtf("Backgroundtask","added "+cid);
            CalendarClassDao calendarClassDao=CalendarClassDao.getInstance(mContext);
            calendarClassDao.createDefaultAppCalendar();

        }else Log.wtf("Backgroundtask","already added "+cid);
        return cid;
    }






}
