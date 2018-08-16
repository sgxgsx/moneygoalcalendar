package com.jimmy.common.data;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;

import com.jimmy.common.bean.CalendarClass;
import com.jimmy.common.bean.EventSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarClassDao {
    private Context mContext;
    private Activity mActivity;

    private CalendarClassDao(Context context) {
        mContext = context;
        mActivity  = (Activity) context;

    }

    public static CalendarClassDao getInstance(Context context) {
        return new CalendarClassDao(context);
    }

    public int addCalendarClass(CalendarClass calendarClass) {
        // add CALENDAR
        int id = 2;                         // calendar id
        // TODO VLAD ФУНКЦИЯ ДОБАВЛЕНИЯ КАЛЛЕНДАРЯ В ОБЩУЮ БД

        return id;
    }


    public List<CalendarClass> getAllCalendars(){
        List<CalendarClass> calendars = new ArrayList<>();

        String[] mProjection =
                {
                        CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.CALENDAR_LOCATION,
                        CalendarContract.Calendars.CALENDAR_TIME_ZONE,
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.CALENDAR_COLOR,
                        CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                        CalendarContract.Calendars.OWNER_ACCOUNT,
                        CalendarContract.Calendars.ACCOUNT_TYPE
                };

        Uri uri = CalendarContract.Calendars.CONTENT_URI;

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
            int id = cur.getInt(5);
            String name = cur.getString(6);
            int color = cur.getInt(7);
            int access_level = cur.getInt(8);
            String owner_account = cur.getString(9);
            String type = cur.getString(10);

            CalendarClass cal = new CalendarClass();
            cal.setAllowedAttendeeTypes(allowed_att);
            cal.setAccountName(accName);
            cal.setDisplayName(displayName);
            cal.setLocation(location);
            cal.setTimeZone(time_zone);
            cal.setId(id);
            cal.setName(name);
            cal.setColor(color);
            cal.setAccess_level(access_level);
            cal.setOwner_account(owner_account);
            cal.setType(type);
            calendars.add(cal);
        }
        return calendars;
    }



    public boolean removeCalendarClass(int calID) {
        // remove calendar
        // TODO VLAD ФУНКЦИЯ УДАЛЕНИЯ КАЛЕНДАРЯ С ОБЩЕЙ БД
        return true;
    }
}
