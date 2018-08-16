package com.jimmy.common.data;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.jimmy.common.bean.CalendarClass;
import com.jimmy.common.bean.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jimmy on 2016/10/11 0011.
 */
public class ScheduleDao {

    private JeekSQLiteHelper mHelper;
    private Context mContext;
    private Activity mActivity;


    private ScheduleDao(Context context) {
        mHelper = new JeekSQLiteHelper(context);
        mContext = context;
        mActivity  = (Activity) context;

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
    }*/

    public List<Schedule> getScheduleByDate(int year, int month, int day, String account){
        List<Schedule> schedules = new ArrayList<>();

        String[] projection = new String[] { CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.EVENT_COLOR,CalendarContract.Events.DISPLAY_COLOR, CalendarContract.Events.EVENT_COLOR_KEY, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.OWNER_ACCOUNT };


        Calendar startTime = Calendar.getInstance();
        startTime.set(year,month,day,0, 0, 0);
    //fix to retrieve events on 00:00:00
        long time = startTime.getTimeInMillis();
        time -= 1000;
    //end fix
        Calendar endTime= Calendar.getInstance();
        endTime.set(year,month,day,23,59, 59);

        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + time + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() +  " ) AND ( " + CalendarContract.Events.OWNER_ACCOUNT + " = " + "'" + account + "'" + " ))";
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_CALENDAR}, 1000);
        }

        Cursor cursor = mContext.getContentResolver().query( CalendarContract.Events.CONTENT_URI, projection, selection, null, null );

// output the events
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setTitle(cursor.getString(1));
                schedule.setTime(cursor.getLong(3));
                schedule.setTime_end(cursor.getLong(4));
                Log.wtf("acc1", cursor.getString(10));
                schedules.add(schedule);
            } while ( cursor.moveToNext());
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
                    Schedule sh = new Schedule(schedules.get(i).getId(), schedules.get(i).getColor(), schedules.get(i).getTitle(), schedules.get(i).getDesc(), schedules.get(i).getLocation(), schedules.get(i).getState(), schedules.get(i).getTime(), schedules.get(i).getTime_end(), schedules.get(i).getYear());
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
        getAllCalendars();
        cursor.close();
        mHelper.close();
        return schedules;

    }

    public List<CalendarClass> getAllCalendars(){
        List<CalendarClass> calendars = new ArrayList<>();

        String[] mProjection =
                {
                        CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.CALENDAR_LOCATION,
                        CalendarContract.Calendars.CALENDAR_TIME_ZONE
                };

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        //String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
        //        + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
        //        + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        //String[] selectionArgs = new String[]{"cal@zoftino.com", "cal.zoftino.com",
        //       "cal@zoftino.com"};

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_CALENDAR}, 1000);
        }
        Cursor cur = mContext.getContentResolver().query(uri, mProjection, null, null, null);

        while (cur.moveToNext()) {
            String allowed_att = cur.getString(0);
            String accName     = cur.getString(1);
            String displayName = cur.getString(2);
            String location = cur.getString(3);
            String time_zone = cur.getString(4);
        /*  Log.wtf("Allowed attendee types", String.valueOf(allowed_att));
            Log.wtf("acc name", accName);
            Log.wtf("display name", displayName);
            Log.wtf("location", location);
            Log.wtf("time zone", time_zone);
            Log.wtf("-----", "------------------");           */

            CalendarClass cal = new CalendarClass();
            cal.setAllowedAttendeeTypes(allowed_att);
            cal.setAccountName(accName);
            cal.setDisplayName(displayName);
            cal.setLocation(location);
            cal.setTimeZone(time_zone);
            calendars.add(cal);
        }
        return calendars;
    }

}
