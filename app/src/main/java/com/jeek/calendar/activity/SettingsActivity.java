package com.jeek.calendar.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.CalendarClassAdapter;
import com.jeek.calendar.task.calendarclass.LoadCalendarClassesTask;
import com.jimmy.common.base.app.BaseActivity;
import com.jimmy.common.bean.CalendarClass;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SettingsActivity extends BaseActivity implements /*View.OnClickListener,*/ OnTaskFinishedListener<List<CalendarClass>> {

    private Toolbar mToolBar;
    private TextView mToolBarTextViewTitle;

    private RecyclerView rvMenuCalendarClassList;   //rvMenuEventSetist      CALENDARS

    private CalendarClassAdapter mCalendarClassAdapter;
    private List<CalendarClass> mCalendarClasses;


    @Override
    protected void bindView() {
        setContentView(R.layout.activity_settings);
        /*
        dlMain = searchViewById(R.id.dlMain);
        llTitleDate = searchViewById(R.id.llTitleDate);
        tvTitleMonth = searchViewById(R.id.tvTitleMonth);
        tvTitleDay = searchViewById(R.id.tvTitleDay);
        tvTitle = searchViewById(R.id.tvTitle);
        */
        mToolBar = searchViewById(R.id.tbSettings);
        mToolBarTextViewTitle = searchViewById(R.id.tvSettingsTitle);
        rvMenuCalendarClassList = searchViewById(R.id.rvSettingsCalendarClasses);
        /*
        mUserNameTextView = searchViewById(R.id.tvMenuTitleAccount);
        searchViewById(R.id.ivMainMenu).setOnClickListener(this);
        searchViewById(R.id.llMenuSchedule).setOnClickListener(this);
        searchViewById(R.id.llMenuNoCategory).setOnClickListener(this);
        searchViewById(R.id.llMenuGoMoney).setOnClickListener(this);
        searchViewById(R.id.tvMenuAddEventSet).setOnClickListener(this);
        searchViewById(R.id.tvMenuSignOut).setOnClickListener(this);
        searchViewById(R.id.tvMenuDeleteAccount).setOnClickListener(this);
        searchViewById(R.id.floatingActionButton).setOnClickListener(this);
        */
        //initUi();
        initCalendarClassesList();
        //gotoScheduleFragment();
        //initBroadcastReceiver();

    }
    private void initCalendarClassesList() {
        mCalendarClasses = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenuCalendarClassList.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvMenuCalendarClassList.setItemAnimator(itemAnimator);
        mCalendarClassAdapter = new CalendarClassAdapter(this, mCalendarClasses);
        rvMenuCalendarClassList.setAdapter(mCalendarClassAdapter);
    }



    @Override
    protected void initData() {
        super.initData();

        new LoadCalendarClassesTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    public void onTaskFinished(List<CalendarClass> data) {
        mCalendarClassAdapter.changeAllData(data);
    }
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
