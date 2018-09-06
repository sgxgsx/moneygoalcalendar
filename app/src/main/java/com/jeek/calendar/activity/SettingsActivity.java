package com.jeek.calendar.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.CalendarClassAdapter;
import com.jimmy.common.SettingsDatabase.CalendarSettingsDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;
import com.jimmy.common.base.app.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends BaseActivity /*implements View.OnClickListener, OnTaskFinishedListener<List<CalendarSettingsEntry>>,  SharedPreferences.OnSharedPreferenceChangeListener */{

    private Toolbar mToolBar;
    private TextView mToolBarTextViewTitle;

    private RecyclerView rvMenuCalendarClassList;   //rvMenuEventSetist      CALENDARS
    private CalendarSettingsDatabase mDb;
    private CalendarClassAdapter mCalendarClassAdapter;
    //private List<CalendarClass> mCalendarClasses;
    private List<CalendarSettingsEntry> mCalendarSettingsEntry;


    @Override
    public void onCreate(Bundle onSaved)
    {
        super.onCreate(onSaved);
        //mCalendarSettingsEntry = CalendarClassDao.getInstance(this).getAllCalendars();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_settings);

        mToolBar = searchViewById(R.id.tbSettings);
        mToolBarTextViewTitle = searchViewById(R.id.tvSettingsTitle);
        rvMenuCalendarClassList = searchViewById(R.id.rvSettingsCalendarClasses);
        initCalendarClassesList();

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }
    private void initCalendarClassesList() {
        mCalendarSettingsEntry = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenuCalendarClassList.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvMenuCalendarClassList.setItemAnimator(itemAnimator);
        mCalendarClassAdapter = new CalendarClassAdapter(this, mCalendarSettingsEntry);
        rvMenuCalendarClassList.setAdapter(mCalendarClassAdapter);
    }



    @Override
    protected void initData() {
        super.initData();
        Log.wtf("tag", "from database");
        mDb = CalendarSettingsDatabase.getInstance(getApplicationContext());
        final LiveData<List<CalendarSettingsEntry>> liveData = mDb.calendarSettingsDao().loadLiveDataSettingsCalendars();
        liveData.observe(this, new Observer<List<CalendarSettingsEntry>>() {
            @Override
            public void onChanged(@Nullable List<CalendarSettingsEntry> calendarSettingsEntries) {
                mCalendarClassAdapter.changeAllData(calendarSettingsEntries);
            }
        });
    }
    /*
    @Override
    public void onTaskFinished(List<CalendarSettingsEntry> data) {
        mCalendarClassAdapter.changeAllData(data);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        if (key.equals(getString(R.string.pref_changed_value_calendars))){
            Log.wtf("Tag", "changed");
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
    */
    /*
    private void initBroadcastReceiver() {
        if (mAddEventSetBroadcastReceiver == null) {
            mAddEventSetBroadcastReceiver = new AddEventSetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ADD_EVENT_SET_ACTION);
            registerReceiver(mAddEventSetBroadcastReceiver, filter);
        }
    }
*/
}
