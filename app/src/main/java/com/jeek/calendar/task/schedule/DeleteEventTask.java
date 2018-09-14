package com.jeek.calendar.task.schedule;

import android.content.Context;
import android.util.Log;

import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.CalendarSystemDatabase.ScheduleDao;
import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.listener.OnTaskFinishedListener;

public class DeleteEventTask extends BaseAsyncTask<Schedule> {
    private Schedule mSchedule;
    public DeleteEventTask(Context context, OnTaskFinishedListener<Schedule> onTaskFinishedListener, Schedule schedule) {
        super(context, onTaskFinishedListener);
        mSchedule = schedule;
        Log.wtf("suka","task1");
    }

    @Override
    protected Schedule doInBackground(Void... params) {
        Log.wtf("suka","task2");
        if (mSchedule != null) {
            ScheduleDao dao = ScheduleDao.getInstance(mContext);
            dao.deleteEvent(mSchedule);
            return mSchedule;
        } else {
            return null;
        }
    }
}
