package com.jeek.calendar.task.schedule;

import android.content.ContentUris;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jeek.calendar.activity.EditEventActivity;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.CalendarSystemDatabase.ScheduleDao;

public class EditEventTask extends AsyncTask<Schedule, Void, Void> {

    protected Context mContext;
    protected Schedule mSchedule;

    public EditEventTask(Context context, Schedule Schedule) {
        mContext = context;
        mSchedule = Schedule;
    }

    @Override
    protected Void doInBackground(Schedule... Schedules) {
        try {
            ScheduleDao mScheduleDao = ScheduleDao.getInstance(mContext);
            mScheduleDao.editEvent(mSchedule);
            Log.wtf("EditEventTask", "processing");
        } catch (Exception e) {
            Log.wtf("EditEventTask", "EXCEPTION");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Log.wtf("EditEventTask", "finished");
    }
}