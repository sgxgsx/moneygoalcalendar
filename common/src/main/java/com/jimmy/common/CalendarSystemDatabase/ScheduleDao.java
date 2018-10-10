package com.jimmy.common.CalendarSystemDatabase;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jimmy.common.R;
import com.jimmy.common.data.JeekDBConfig;
import com.jimmy.common.data.JeekSQLiteHelper;
import com.jimmy.common.util.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Jimmy on 2016/10/11 0011.
 */
public class  ScheduleDao{

    private JeekSQLiteHelper mHelper;
    private Context mContext;
    private Activity mActivity;
    private CalendarClassDao mCalendarClassDao;

    private ScheduleDao(Context context) {
        mHelper = new JeekSQLiteHelper(context);
        mContext = context;
        mActivity  = (Activity) context;
        mCalendarClassDao = CalendarClassDao.getInstance(context);
    }

    public static ScheduleDao getInstance(Context context) {
        return new ScheduleDao(context);
    }

    public int addSchedule(Schedule schedule) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JeekDBConfig.SCHEDULE_TITLE, schedule.getTitle());
        values.put(JeekDBConfig.SCHEDULE_COLOR, schedule.getColor());
        values.put(JeekDBConfig.SCHEDULE_DESC, schedule.getDesc());
        values.put(JeekDBConfig.SCHEDULE_STATE, schedule.getState());
        values.put(JeekDBConfig.SCHEDULE_LOCATION, schedule.getLocation());
        values.put(JeekDBConfig.SCHEDULE_TIME, schedule.getTime());
        values.put(JeekDBConfig.SCHEDULE_YEAR, schedule.getYear());
        values.put(JeekDBConfig.SCHEDULE_MONTH, schedule.getMonth());
        values.put(JeekDBConfig.SCHEDULE_DAY, schedule.getDay());
        values.put(JeekDBConfig.SCHEDULE_EVENT_SET_ID, schedule.getEventSetId());
        long row = db.insert(JeekDBConfig.SCHEDULE_TABLE_NAME, null, values);
        db.close();
        return row > 0 ? getLastScheduleId() : 0;
    }







    private int getLastScheduleId() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JeekDBConfig.SCHEDULE_TABLE_NAME, null, null, null, null, null, null, null);
        int id = 0;
        if (cursor.moveToLast()) {
            id = cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_ID));
        }
        cursor.close();
        db.close();
        mHelper.close();
        return id;
    }



    public List<Integer> getTaskHintByMonth(int year, int month) {
        List<Integer> taskHint = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JeekDBConfig.SCHEDULE_TABLE_NAME, new String[]{JeekDBConfig.SCHEDULE_DAY},
                String.format("%s=? and %s=?", JeekDBConfig.SCHEDULE_YEAR,
                        JeekDBConfig.SCHEDULE_MONTH), new String[]{String.valueOf(year), String.valueOf(month)}, null, null, null);
        while (cursor.moveToNext()) {
            taskHint.add(cursor.getInt(0));
        }
        cursor.close();
        db.close();
        mHelper.close();
        return taskHint;
    }

    public List<Integer> getTaskHintByWeek(int firstYear, int firstMonth, int firstDay, int endYear, int endMonth, int endDay) {
        List<Integer> taskHint = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor1 = db.query(JeekDBConfig.SCHEDULE_TABLE_NAME, new String[]{JeekDBConfig.SCHEDULE_DAY},
                String.format("%s=? and %s=? and %s>=?", JeekDBConfig.SCHEDULE_YEAR, JeekDBConfig.SCHEDULE_MONTH, JeekDBConfig.SCHEDULE_DAY),
                new String[]{String.valueOf(firstYear), String.valueOf(firstMonth), String.valueOf(firstDay)}, null, null, null);
        while (cursor1.moveToNext()) {
            taskHint.add(cursor1.getInt(0));
        }
        cursor1.close();
        Cursor cursor2 = db.query(JeekDBConfig.SCHEDULE_TABLE_NAME, new String[]{JeekDBConfig.SCHEDULE_DAY},
                String.format("%s=? and %s=? and %s<=?", JeekDBConfig.SCHEDULE_YEAR, JeekDBConfig.SCHEDULE_MONTH, JeekDBConfig.SCHEDULE_DAY),
                new String[]{String.valueOf(endYear), String.valueOf(endMonth), String.valueOf(endDay)}, null, null, null);
        while (cursor2.moveToNext()) {
            taskHint.add(cursor2.getInt(0));
        }
        cursor2.close();
        db.close();
        mHelper.close();
        return taskHint;
    }

    public void addEvent(Schedule mSchedule) {

        ContentResolver cr = mContext.getContentResolver();

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(mSchedule.getYear(), mSchedule.getMonth(), mSchedule.getDay(), 9, 30);

        Calendar endTime = Calendar.getInstance();
        endTime.set(mSchedule.getYear(), mSchedule.getMonth(), mSchedule.getDay(), 10, 30);

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
        values.put(CalendarContract.Events.TITLE, mSchedule.getTitle());
        values.put(CalendarContract.Events.DESCRIPTION, mSchedule.getDesc());
        values.put(CalendarContract.Events.CALENDAR_ID, 238);
        //todo сделать нормальный алгоритм присваивания ID эвенту(например через время создания)
        values.put(CalendarContract.Events._ID, (int)Math.random()*999999);
        TimeZone tz = TimeZone.getDefault();
        values.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getDisplayName(Locale.getDefault(Locale.Category.DISPLAY)));
        values.put(CalendarContract.Events.EVENT_LOCATION, mSchedule.getLocation());
        /*values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, "1");
        values.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, "1");*/
        values.put(CalendarContract.Events.EVENT_COLOR, -552015);
        /*values.put(CalendarContract.Events.);*/

        Log.wtf("ScheduleDao","values.put");
        cr.insert(CalendarContract.Events.CONTENT_URI, values);
        Log.wtf("ScheduleDao","cr.instert");
    }

    public void deleteEvent(Schedule mSchedule) {
        //todo сделать синхронизацию с гуглом(если надр будет)
        Log.wtf("suka","dao");
        Uri uri = CalendarContract.Events.CONTENT_URI;

        String mSelectionClause = CalendarContract.Events._ID+ " = ?";
        String[] mSelectionArgs = {Integer.toString(mSchedule.getId())};

        int updCount = mContext.getContentResolver().delete(uri,mSelectionClause,mSelectionArgs);
    }

    public boolean removeSchedule(long id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int row = db.delete(JeekDBConfig.SCHEDULE_TABLE_NAME, String.format("%s=?", JeekDBConfig.SCHEDULE_ID), new String[]{String.valueOf(id)});
        db.close();
        mHelper.close();
        return row != 0;
    }

    public void removeScheduleByEventSetId(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(JeekDBConfig.SCHEDULE_TABLE_NAME, String.format("%s=?", JeekDBConfig.SCHEDULE_EVENT_SET_ID), new String[]{String.valueOf(id)});
        db.close();
        mHelper.close();
    }

    public boolean updateSchedule(Schedule schedule) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JeekDBConfig.SCHEDULE_TITLE, schedule.getTitle());
        values.put(JeekDBConfig.SCHEDULE_COLOR, schedule.getColor());
        values.put(JeekDBConfig.SCHEDULE_DESC, schedule.getDesc());
        values.put(JeekDBConfig.SCHEDULE_STATE, schedule.getState());
        values.put(JeekDBConfig.SCHEDULE_LOCATION, schedule.getLocation());
        values.put(JeekDBConfig.SCHEDULE_YEAR, schedule.getYear());
        values.put(JeekDBConfig.SCHEDULE_MONTH, schedule.getMonth());
        values.put(JeekDBConfig.SCHEDULE_TIME, schedule.getTime());
        values.put(JeekDBConfig.SCHEDULE_DAY, schedule.getDay());
        values.put(JeekDBConfig.SCHEDULE_EVENT_SET_ID, schedule.getEventSetId());
        int row = db.update(JeekDBConfig.SCHEDULE_TABLE_NAME, values, String.format("%s=?", JeekDBConfig.SCHEDULE_ID), new String[]{String.valueOf(schedule.getId())});
        db.close();
        mHelper.close();
        return row > 0;
    }

    public void editEvent(Schedule mSchedule) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(mSchedule.getYear(), mSchedule.getMonth(), mSchedule.getDay(), 9, 30);

        Calendar endTime = Calendar.getInstance();
        endTime.set(mSchedule.getYear(), mSchedule.getMonth(), mSchedule.getDay(), 10, 30);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
        contentValues.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
        contentValues.put(CalendarContract.Events.TITLE, mSchedule.getTitle());
        contentValues.put(CalendarContract.Events.DESCRIPTION, mSchedule.getDesc());
        contentValues.put(CalendarContract.Events.CALENDAR_ID, 1);
        contentValues.put(CalendarContract.Events._ID, mSchedule.getId());
        TimeZone tz = TimeZone.getDefault();
        contentValues.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getDisplayName(Locale.getDefault(Locale.Category.DISPLAY)));
        contentValues.put(CalendarContract.Events.EVENT_LOCATION, mSchedule.getLocation());
        /*values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, "1");
        values.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, "1");*/
        contentValues.put(CalendarContract.Events.EVENT_COLOR, mSchedule.getColor());

        Uri uri = CalendarContract.Events.CONTENT_URI;

        String mSelectionClause = CalendarContract.Events._ID+ " = ?";
        String[] mSelectionArgs = {Integer.toString(mSchedule.getId())};

        int updCount = mContext.getContentResolver().update(uri, contentValues,mSelectionClause,mSelectionArgs);

    }



    public List<Schedule> getScheduleByEventSetId(int id) {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JeekDBConfig.SCHEDULE_TABLE_NAME, null,
                String.format("%s=?", JeekDBConfig.SCHEDULE_EVENT_SET_ID), new String[]{String.valueOf(id)}, null, null, null);
        Schedule schedule;
        while (cursor.moveToNext()) {
            schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_ID)));
            schedule.setColor(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_COLOR)));
            schedule.setTitle(cursor.getString(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_TITLE)));
            schedule.setDesc(cursor.getString(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_DESC)));
            schedule.setLocation(cursor.getString(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_LOCATION)));
            schedule.setState(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_STATE)));
            schedule.setYear(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_YEAR)));
            schedule.setMonth(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_MONTH)));
            schedule.setDay(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_DAY)));
            schedule.setTime(cursor.getLong(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_TIME)));
            schedule.setEventSetId(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_EVENT_SET_ID)));
            schedules.add(schedule);
        }
        cursor.close();
        db.close();
        mHelper.close();
        return schedules;
    }

/*public List<Schedule> getScheduleByDate(int year, int month, int day) {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JeekDBConfig.SCHEDULE_TABLE_NAME, null,
                String.format("%s=? and %s=? and %s=?", JeekDBConfig.SCHEDULE_YEAR,
                        JeekDBConfig.SCHEDULE_MONTH, JeekDBConfig.SCHEDULE_DAY), new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day)}, null, null, null);
        Schedule schedule;
        while (cursor.moveToNext()) {
            schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_ID)));
            schedule.setColor(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_COLOR)));
            schedule.setTitle(cursor.getString(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_TITLE)));
            schedule.setLocation(cursor.getString(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_LOCATION)));
            schedule.setDesc(cursor.getString(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_DESC)));
            schedule.setState(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_STATE)));
            schedule.setYear(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_YEAR)));
            schedule.setMonth(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_MONTH)));
            schedule.setDay(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_DAY)));
            schedule.setTime(cursor.getLong(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_TIME)));
            schedule.setEventSetId(cursor.getInt(cursor.getColumnIndex(JeekDBConfig.SCHEDULE_EVENT_SET_ID)));
            schedules.add(schedule);
        }
        cursor.close();
        db.close();
        mHelper.close();
        return schedules;
    }
    public int getEventColor(String color){
        String[] proj = new String[]{CalendarContract.Colors.COLOR};
        String selection ="( " + CalendarContract.Colors.COLOR_KEY + " = " + color + " )";
        Cursor cursor = mContext.getContentResolver().query(CalendarContract.Colors.CONTENT_URI, proj, selection, null, null);
        if (cursor.moveToNext()){
            return cursor.getInt(0);
        } else{
            Log.wtf("RETURN COLOR", "4166095");
            return 4166095;
        }
    }*/

    public List<Schedule> getScheduleByDate(int year, int month, int day, String Account){
        List<Schedule> schedules = new ArrayList<>();
        List<CalendarClass> calendarClasses = mCalendarClassDao.getTrueCalendars();
        /*if(Account.equals("ANONYMOUS")){
            return schedules;
        }*/
        String[] projection = new String[] { CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.DISPLAY_COLOR, CalendarContract.Events.EVENT_COLOR, CalendarContract.Events.EVENT_COLOR_KEY, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.OWNER_ACCOUNT, CalendarContract.Events.RRULE, CalendarContract.Events.ACCOUNT_NAME};


        Calendar startTime = Calendar.getInstance();
        startTime.set(year,month,day,0, 0, 0);
        //fix to retrieve events on 00:00:00
        long time = startTime.getTimeInMillis();
        time -= 1000;
        //end fix
        Calendar endTime= Calendar.getInstance();
        endTime.set(year,month,day,23,59, 59);


        for(int i = 0; i <calendarClasses.size(); ++i){
            String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + time + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() +  " ) AND ( " + CalendarContract.Events.CALENDAR_ID + " = " + "'" + calendarClasses.get(i).getId() + "'" + " ))";
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_CALENDAR}, 1000);
            }

            Cursor cursor = mContext.getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null );


            if (cursor.moveToFirst()) {
                do {
                    Schedule schedule = new Schedule();
                    schedule.setTitle(cursor.getString(1));
                    schedule.setDesc(cursor.getString(2));
                    schedule.setTime(cursor.getLong(3));
                    schedule.setTime_end(cursor.getLong(4));
                    schedule.setColor(cursor.getInt(5));
                    schedule.setLocation(cursor.getString(9));
                    schedule.setAccount(cursor.getString(10));
                    schedule.setRepeat(cursor.getString(11));
                    schedule.setAccount_name(cursor.getString(12));
                    schedules.add(schedule);
                } while ( cursor.moveToNext());
            }
            cursor.close();
        }

        Log.wtf("wtf", String.valueOf(schedules.size()));
        // sort


        if(schedules.size() > 1){
            int i = 0;
            int goodPairsCounter = 0;
            while (true) {
                long time1 = schedules.get(i).getTime();
                long time2 = schedules.get(i + 1).getTime();
                if (time1 > time2) {
                    Schedule sh = new Schedule(schedules.get(i).getId(), schedules.get(i).getColor(), schedules.get(i).getTitle(), schedules.get(i).getDesc(), schedules.get(i).getLocation(), schedules.get(i).getState(), schedules.get(i).getTime(), schedules.get(i).getTime_end(), schedules.get(i).getYear(), schedules.get(i).getRepeat(), schedules.get(i).getAccount());
                    schedules.remove(i);
                    schedules.add(i + 1, sh);
                    goodPairsCounter = 0;
                } else {
                    goodPairsCounter++;
                }
                i++;
                if (i == schedules.size() - 1) {
                    i = 0;
                }
                if (goodPairsCounter == schedules.size() - 1) break;

            }
        }

        return schedules;

    }


}
