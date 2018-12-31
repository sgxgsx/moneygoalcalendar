package com.jeek.calendar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.task.schedule.AddEventTask;
import com.jeek.calendar.task.schedule.DeleteEventTask;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.CalendarSystemDatabase.ScheduleDao;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;



public class DetailEventActivity extends AppCompatActivity implements View.OnClickListener, OnTaskFinishedListener<Schedule> {
    public static String EVENT_SET_OBJ = "event.set.obj";
    public static final String SCHEDULE_OBJ = "Schedule.Event";

    private TextView mtbTextView, mtvEventTitle, mtvStartTime, mtvEndTime, mtvRepeat;
    private TextView mtvNote, mtvLocation, mtvCalendarOwner, mtvNotification;
    private ConstraintLayout mclTextTitle, mclDateLayout, mclNotificationLayout, mclLocationLayout, mclNoteLayout, mclOwnerLayout;
    private ScrollView mScrollView;
    private ImageView mImageColor;



    private Schedule mSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);


        mtvEventTitle = findViewById(R.id.tvEventTitle);
        mtvStartTime = findViewById(R.id.tvStartTime);
        mtvEndTime = findViewById(R.id.tvEndTime);
        mtvRepeat = findViewById(R.id.tvRepeat);
        mtvNote = findViewById(R.id.tvNoteTextView);
        mtvLocation = findViewById(R.id.tvLocationTextView);
        mtvCalendarOwner = findViewById(R.id.tvCalendarOwnerTextView);
        mtvNotification = findViewById(R.id.tvNotificationTextView);

        mImageColor  = findViewById(R.id.ivName);
        mclTextTitle = findViewById(R.id.EventTitleLayout);
        mclLocationLayout = findViewById(R.id.LocationLayout);
        mclNoteLayout = findViewById(R.id.DescriptionLayout);
        mclOwnerLayout = findViewById(R.id.AccountLayout);
        mclDateLayout = findViewById(R.id.DateLayout);
        mclNotificationLayout = findViewById(R.id.NotificationLayout);

        mScrollView = findViewById(R.id.svEventDetail);
        findViewById(R.id.llCancel).setOnClickListener(this);
        findViewById(R.id.llEdit).setOnClickListener(this);
        findViewById(R.id.llDelete).setOnClickListener(this);
        findViewById(R.id.llDuplicate).setOnClickListener(this);


        // TODO доработать с меню в тулбаре с разными ивентами

        if (getIntent().hasExtra(SCHEDULE_OBJ)) {
            mSchedule = (Schedule) getIntent().getSerializableExtra(SCHEDULE_OBJ);
            initUI();
        }


    }


    private void initUI() {

        Log.wtf("COlor", String.valueOf(mSchedule.getColor()));


        Date date = new Date(mSchedule.getTime());
        Date date_end = new Date(mSchedule.getTime_end());
        String repeat = mSchedule.getRepeat();
        String location = mSchedule.getLocation();
        String description = mSchedule.getDesc();
        String owner = mSchedule.getAccount();
        String title = mSchedule.getTitle();
        String notification = null;


        mtvEventTitle.setText(title);
        Format format = new SimpleDateFormat("EEEE, MMMM d, HH:mm");
        Format format_end = new SimpleDateFormat("EEEE, MMMM d, HH:mm");
        mtvStartTime.setText(format.format(date) + " -");
        mtvEndTime.setText(format_end.format(date_end));

        mImageColor.setImageTintList(ColorStateList.valueOf(mSchedule.getColor()));;

        ifHideLayout(repeat, null, mtvRepeat);
        ifHideLayout(location, mclLocationLayout, mtvLocation);
        ifHideLayout(description, mclNoteLayout, mtvNote);
        ifHideLayout(owner, mclOwnerLayout, mtvCalendarOwner);
        ifHideLayout(notification, mclNotificationLayout, mtvNotification);
    }

    private void ifHideLayout(String param, ConstraintLayout layout, TextView textView) {
        try {
            if (param != null && param.length() > 0) {
                textView.setText(param);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            if (layout == null) {
                textView.setVisibility(View.GONE);
            } else {
                layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.llDelete:
                deleteEvent();
                break;
            case R.id.llCancel:
                finish();
                break;
            case R.id.llDuplicate:
                duplicateEvent();
                break;
            case R.id.llEdit:
                editEvent();
                break;
        }
    }

    private void editEvent() {
        Intent intent = new Intent(this, EditEventActivity.class);
        startActivityForResult(intent, 1);
    }

    public void deleteEvent(){
        Log.wtf("suka","activitydelete1");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 999);
        }
        new DeleteEventTask(this, this, mSchedule).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void duplicateEvent(){
        // TODO функцию создания дубликата ивента
        ;
    }

    @Override
    public void onTaskFinished(Schedule data) {
        setResult(1, new Intent().putExtra(EVENT_SET_OBJ, data));
        finish();
    }


}
