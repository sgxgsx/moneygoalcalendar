package com.jeek.calendar.task.schedule;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.CalendarSystemDatabase.ScheduleDao;
import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.listener.OnTaskFinishedListener;

public class DeleteEventTask extends AsyncTask<Void, Void, Void> {
    private Schedule mSchedule;
    private Context mContext;

    public DeleteEventTask(Context context, Schedule schedule) {
        super();
        mContext  = context;
        mSchedule = schedule;
    }

    @Override
    protected Void doInBackground(Void...voids) {
        Log.wtf("suka","task2");
        if (mSchedule != null) {
            ScheduleDao dao = ScheduleDao.getInstance(mContext);
            dao.deleteEvent(mSchedule);
        }
        return null;
    }
}
