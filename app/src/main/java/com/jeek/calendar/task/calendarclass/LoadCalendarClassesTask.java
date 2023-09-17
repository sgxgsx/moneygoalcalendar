package com.jeek.calendar.task.calendarclass;

import android.content.Context;

import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.CalendarSystemDatabase.CalendarClass;
import com.jimmy.common.CalendarSystemDatabase.CalendarClassDao;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.List;

public class LoadCalendarClassesTask extends BaseAsyncTask<List<CalendarClass>> {
    private Context mContext;

    public LoadCalendarClassesTask(Context context, OnTaskFinishedListener<List<CalendarClass>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected List<CalendarClass> doInBackground(Void... params) {
        CalendarClassDao dao = CalendarClassDao.getInstance(mContext);
        return dao.getTrueCalendars();
    }
}
