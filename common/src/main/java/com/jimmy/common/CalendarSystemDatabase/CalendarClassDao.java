package com.jimmy.common.CalendarSystemDatabase;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.ArrayMap;
import android.util.Log;

import com.jimmy.common.SettingsDatabase.CalendarSettingsDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class  CalendarClassDao {
    private Context mContext;
    private Activity mActivity;
    private CalendarSettingsDatabase mCalendarSettingsDatabase;
    private ArrayList<Tuple> mSavedCalendars;

    private CalendarClassDao(Context context) {
        mContext = context;
        mActivity  = (Activity) context;
        mCalendarSettingsDatabase = CalendarSettingsDatabase.getInstance(context);
        mSavedCalendars = getSavedCalendars();
    }

    public List<CalendarSettingsEntry> getAllCalendars(){
        return mCalendarSettingsDatabase.calendarSettingsDao().loadSettingsCalendars();
    }

    public static CalendarClassDao getInstance(Context context) {
        return new CalendarClassDao(context);
    }

    class Tuple{
        int id;
        boolean show;

        public Tuple(int id, boolean show) {
            this.id = id;
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }
    }

    public int addCalendarClass(CalendarClass calendarClass) {
        // add CALENDAR
        int id = 2;                         // calendar id
        // T ODO VLAD ФУНКЦИЯ ДОБАВЛЕНИЯ КАЛЛЕНДАРЯ В ОБЩУЮ БД

        return id;
    }
    /*
        public int[] getCalendarId(){
            String[] mProjection = {CalendarContract.Calendars._ID};
            Uri uri = CalendarContract.Calendars.CONTENT_URI;
            Cursor cur = mContext.getContentResolver().query(uri, mProjection, null, null, null);
            int amount = 0;
            while (cur.moveToNext()){
                amount++;
            }
            cur.moveToFirst();
            int[] return_it = new int[amount];
            amount = 0;
            while (cur.moveToNext()){
                return_it[amount] = cur.getInt(0);
                amount++;
            }
            return return_it;
        } */
    public ArrayList<Tuple> getSavedCalendars(){
        ArrayList<Tuple> savedCalendar = new ArrayList<>();
        List<CalendarSettingsEntry> list = mCalendarSettingsDatabase.calendarSettingsDao().loadSettingsCalendars();
        for(int i=0; i<list.size(); ++i){
            savedCalendar.add(new Tuple(list.get(i).getCalendarID(), list.get(i).isShow()));
        }
        return savedCalendar;

    }

    private int ifInCalendars(int id){
        for(int i=0; i < mSavedCalendars.size(); ++i){
            if(mSavedCalendars.get(i).getId() == id){
                if(mSavedCalendars.get(i).isShow()){
                    return 2;
                }
                return 1;
            }
        }
        return 0;
    }

    public List<Integer> getAllCalendarsIDs(){
        List<Integer> calendars = new ArrayList<>();
        String[] mProjection = {CalendarContract.Calendars._ID};
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_CALENDAR}, 1000);
        }
        Cursor cur = mContext.getContentResolver().query(uri, mProjection, null, null, null);
        while (cur.moveToNext()){
            calendars.add(cur.getInt(0));
        }
        return calendars;
    }

    public CalendarClass getCalendarByID(int id){
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
        String selection = "( " + CalendarContract.Calendars._ID + "=" + id +" )";
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_CALENDAR}, 1000);
        }
        Cursor cur = mContext.getContentResolver().query(uri, mProjection, selection, null, null);
        CalendarClass cal = new CalendarClass();

        while (cur.moveToNext()){
            String allowed_att = cur.getString(0);
            String accName     = cur.getString(1);
            String displayName = cur.getString(2);
            String location = cur.getString(3);
            String time_zone = cur.getString(4);
            String name = cur.getString(6);
            int color = cur.getInt(7);
            int access_level = cur.getInt(8);
            String owner_account = cur.getString(9);
            String type = cur.getString(10);


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
        }
        return cal;
    }


    public List<CalendarClass> getTrueCalendars(){
        List<CalendarClass> calendarClassList = new ArrayList<>();
        List<Integer> ids = getAllCalendarsIDs();
        for(int i=0; i < ids.size(); ++i){
            int id = ids.get(i);
            int answer = ifInCalendars(id);

            if(answer == 0){
                CalendarClass calendarClass = getCalendarByID(id);
                CalendarSettingsEntry calendarSettingsEntry = new CalendarSettingsEntry(id, calendarClass.getName(), true);
                mCalendarSettingsDatabase.calendarSettingsDao().insertSettingsCalendar(calendarSettingsEntry);
                mSavedCalendars.add(new Tuple(id, true));
                calendarClassList.add(calendarClass);
            } else {
                if(answer == 2){
                    calendarClassList.add(getCalendarByID(id));
                }
            }
        }
        return calendarClassList;
    }


    // completed 1 VLAD сделать метод getAllCalendars просто для проверки есть ли такой календарь у нас, если нет то
    // completed 2 VLAD сделать query getCalendarByID и добавить календарь в CalendarSettingsDatabase
    // comleted 3 VLAD сделать метод getTrueCalendars в CalendarSettingsDao который возвращает календари где поставлена галочка true.
    // DONE 4 VLAD сделать метод getEventsInTrueCalendars в ScheduleDao который возвращает ивенты только в Календарях с галочкой true
    // DONE 5 VLAD сделать task LoadCalendarSettingsTask в app который загружает все календари с CalendarSettingsDao.


/*
    public boolean removeCalendarClass(int calID) {
        // remove calendar
        // T ODO VLAD ФУНКЦИЯ УДАЛЕНИЯ КАЛЕНДАРЯ С ОБЩЕЙ БД
        return true;
    }
    */

        public void createDefaultAppCalendar() {
            Log.wtf("createDefaultAppCalendar","start");
            ContentResolver cr = mContext.getContentResolver();
            ContentValues contentValues = new ContentValues();
            //TODO Leha zaebashit' APPNAME suda
            contentValues.put(CalendarContract.Calendars._ID, 238);
            contentValues.put(CalendarContract.Calendars.ACCOUNT_NAME, "cal@zoftino.com");
            contentValues.put(CalendarContract.Calendars.ACCOUNT_TYPE, "cal.zoftino.com");
            contentValues.put(CalendarContract.Calendars.NAME, "zoftino calendar");
            contentValues.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Zoftino.com Calendar");
            contentValues.put(CalendarContract.Calendars.CALENDAR_COLOR, "232323");
            contentValues.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
            contentValues.put(CalendarContract.Calendars.OWNER_ACCOUNT, "cal@zoftino.com");
            contentValues.put(CalendarContract.Calendars.ALLOWED_REMINDERS, "METHOD_ALERT, METHOD_EMAIL, METHOD_ALARM");
            contentValues.put(CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES, "1,2,3");
            contentValues.put(CalendarContract.Calendars.ALLOWED_AVAILABILITY, "AVAILABILITY_BUSY, AVAILABILITY_FREE, AVAILABILITY_TENTATIVE");



            Uri uri = CalendarContract.Calendars.CONTENT_URI;

            uri = uri.buildUpon().appendQueryParameter(android.provider.CalendarContract.CALLER_IS_SYNCADAPTER,"true")
                    .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "cal@zoftino.com")
                    .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "cal.zoftino.com").build();
            cr.insert(uri, contentValues);
            Log.wtf("createDefaultAppCalendar","inserted");
        }

    public boolean defaultCalendarCreated() {
        Cursor cur = null;
        ContentResolver cr = mContext.getContentResolver();

        String[] mProjection =
                {
                        CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.CALENDAR_LOCATION,
                        CalendarContract.Calendars.CALENDAR_TIME_ZONE
                };

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{"cal@zoftino.com", "cal.zoftino.com",
                "cal@zoftino.com"};

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, MY_CAL_REQ);
        }*/

        cur = cr.query(uri, mProjection, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            String accountType = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.ACCOUNT_TYPE));
            if (accountType=="nonlogined") return true;
        }
        return false;
    }

}
