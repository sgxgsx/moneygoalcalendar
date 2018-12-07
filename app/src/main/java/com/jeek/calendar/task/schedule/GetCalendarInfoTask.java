package com.jeek.calendar.task.schedule;

        import android.content.Context;
        import android.util.Log;

        import com.jimmy.common.CalendarSystemDatabase.Schedule;
        import com.jimmy.common.CalendarSystemDatabase.ScheduleDao;
        import com.jimmy.common.CalendarSystemDatabase.TupleCalendar;
        import com.jimmy.common.base.task.BaseAsyncTask;
        import com.jimmy.common.listener.OnTaskFinishedListener;

        import java.util.List;

public class GetCalendarInfoTask extends BaseAsyncTask<String[][]> {
    public GetCalendarInfoTask(Context context, OnTaskFinishedListener<String[][]> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        Log.wtf("OK","addeventtask1");
    }

    @Override
    protected String[][] doInBackground(Void... params) {

            ScheduleDao dao = ScheduleDao.getInstance(mContext);
            String[][] calendars;
            calendars = dao.getAllCalendarsIDsAndNames();
            return calendars;

    }

}
