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
import java.util.Calendar;
import java.util.Date;



public class DetailEventActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EVENT_SET_OBJ = "event.set.obj";
    public static final String SCHEDULE_OBJ = "Schedule.Event";

    private TextView mtbTextView, mtvEventTitle, mtvStartTime, mtvEndTime, mtvRepeat;
    private TextView mtvNote, mtvLocation, mtvCalendarOwner, mtvNotification;
    private ConstraintLayout mclTextTitle, mclDateLayout, mclNotificationLayout, mclLocationLayout, mclNoteLayout, mclOwnerLayout;
    private ScrollView mScrollView;
    private ImageView mImageColor;
    private Toolbar mToolbar;



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

        mToolbar = findViewById(R.id.tbDetailActivity);
        mToolbar.inflateMenu(R.menu.detail_event_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.MenuDeleteEvent:
                        deleteEvent();
                        break;
                    case R.id.MenuDuplicateEvent:
                        duplicateEvent();
                        break;
                    case R.id.MenuUpdateEvent:
                        editEvent();
                }
                return false;
            }
        });
        findViewById(R.id.llCancel).setOnClickListener(this);


        if (getIntent().hasExtra(SCHEDULE_OBJ)) {
            mSchedule = (Schedule) getIntent().getSerializableExtra(SCHEDULE_OBJ);

            initUI();
        }


    }


    private void initUI() {

        Log.wtf("Color", String.valueOf(mSchedule.getColor()));


        Date date = new Date(mSchedule.getTime());
        Date date_end = new Date(mSchedule.getTime_end());
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);

        cal2.setTime(date_end);
        mSchedule.setMinute(cal1.get(Calendar.MINUTE));
        mSchedule.setYear(cal1.get(Calendar.YEAR));
        mSchedule.setHour(cal1.get(Calendar.HOUR_OF_DAY));
        mSchedule.setDay(cal1.get(Calendar.DAY_OF_MONTH));
        mSchedule.setMonth(cal1.get(Calendar.MONTH));
        mSchedule.setMinuteend(cal2.get(Calendar.MINUTE));
        mSchedule.setYearend(cal2.get(Calendar.YEAR));
        mSchedule.setHourend(cal2.get(Calendar.HOUR_OF_DAY));
        mSchedule.setDayend(cal2.get(Calendar.DAY_OF_MONTH));
        mSchedule.setMonthend(cal2.get(Calendar.MONTH));




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
            case R.id.llCancel:
                finish();
                break;
        }
    }

    private void editEvent() {
        // TODO перенаправить на эдитИвентАктивити
        ;
    }

    public void deleteEvent(){
        Log.wtf("suka","activitydelete1");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 999);
        }
        new DeleteEventTask(this, mSchedule).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finish();
    }

    private void duplicateEvent(){
        // TODO функцию создания дубликата ивента
        ;
    }


}
