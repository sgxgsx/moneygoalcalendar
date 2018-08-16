package com.jeek.calendar.task.calendarclass;

import android.content.Context;

import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.bean.CalendarClass;
import com.jimmy.common.data.CalendarClassDao;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.List;

public class AddCalendarClassTask extends BaseAsyncTask<CalendarClass> {

    private CalendarClass mCalendarClass;

    public AddCalendarClassTask(Context context, OnTaskFinishedListener<CalendarClass> onTaskFinishedListener, CalendarClass calendarClass) {
        super(context, onTaskFinishedListener);
        mCalendarClass = calendarClass;
    }

    @Override
    protected CalendarClass doInBackground(Void... params) {
        if (mCalendarClass != null) {
            CalendarClassDao dao = CalendarClassDao.getInstance(mContext);
            int id = dao.addCalendarClass(mCalendarClass);
            if (id != 0) {
                mCalendarClass.setId(id);
                return mCalendarClass;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
