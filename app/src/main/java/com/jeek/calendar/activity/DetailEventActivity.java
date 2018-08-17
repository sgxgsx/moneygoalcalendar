package com.jeek.calendar.activity;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jimmy.common.bean.Schedule;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailEventActivity extends AppCompatActivity {

    public static final String SCHEDULE_OBJ = "Schedule.Event";



    private Toolbar mToolbar;
    private TextView mtbTextView, mtvEventTitle, mtvStartTime, mtvEndTime, mtvRepeat;
    private TextView mtvNote, mtvLocation, mtvCalendarName, mtvCalendarOwner;
    private ConstraintLayout mclTextTitle, mclDateLayout, mclNotificationLayout, mclLocationLayout, mclNoteLayout, mclOwnerLayout;
    private ScrollView mScrollView;
    private ImageView NotificationImage;


    private Schedule mSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        mToolbar = findViewById(R.id.tbDetailActivity);
        mtbTextView   = findViewById(R.id.tbTitleTest);
        mtvEventTitle = findViewById(R.id.tvEventTitle);
        mtvStartTime  = findViewById(R.id.tvStartTime);
        mtvEndTime = findViewById(R.id.tvEndTime);
        mtvRepeat  = findViewById(R.id.tvRepeat);
        mtvNote    = findViewById(R.id.tvNoteTextView);
        mtvLocation = findViewById(R.id.tvLocationTextView);
        mtvCalendarName = findViewById(R.id.tvCalendarNameTextView);
        mtvCalendarOwner = findViewById(R.id.tvCalendarOwnerTextView);

        mclTextTitle = findViewById(R.id.EventTitleLayout);
        mclLocationLayout = findViewById(R.id.LocationLayout);
        mclNoteLayout = findViewById(R.id.DescriptionLayout);
        mclOwnerLayout = findViewById(R.id.AccountLayout);
        mclDateLayout = findViewById(R.id.DateLayout);
        mclNotificationLayout = findViewById(R.id.NotificationLayout);

        NotificationImage = findViewById(R.id.iNotificationImage);

        mScrollView = findViewById(R.id.svEventDetail);


        if(getIntent().hasExtra(SCHEDULE_OBJ)){
            mSchedule = (Schedule) getIntent().getSerializableExtra(SCHEDULE_OBJ);
            initUI();
        }


    }

    private void initUI(){

        Log.wtf("COlor", String.valueOf(mSchedule.getColor()));


        Date date = new Date(mSchedule.getTime());
        Date date_end = new Date(mSchedule.getTime_end());
        String repeat = mSchedule.getRepeat();
        String location = mSchedule.getLocation();
        String description = mSchedule.getDesc();
        String owner = mSchedule.getAccount();
        String owner_name = mSchedule.getAccount_name();
        String title = mSchedule.getTitle();

        Log.wtf("", repeat);
        Log.wtf("s", location);
        Log.wtf("s", description);
        Log.wtf("s", owner);
        Log.wtf("s", owner_name);


        mtvEventTitle.setText(title);
        Format format = new SimpleDateFormat("EEEE, MMMM d, HH:mm");
        Format format_end = new SimpleDateFormat("EEEE, MMMM d, HH:mm");
        mtvStartTime.setText(format.format(date) + " -");
        mtvEndTime.setText(format_end.format(date_end));



        mclTextTitle.setBackgroundColor(mSchedule.getColor());
        mToolbar.setBackgroundColor(mSchedule.getColor());
        if(repeat != null){
            mtvRepeat.setText(repeat);
        } else{
            mtvRepeat.setVisibility(View.INVISIBLE);
        }
        if(location != null){
            mtvLocation.setText(location);
        } else{
            mclLocationLayout.setVisibility(View.GONE);
            mtvLocation = null;
            mclLocationLayout.setMaxWidth(0);
        }
        if(description != null){
            mtvNote.setText(description);
        } else{
            mclNoteLayout.setVisibility(View.INVISIBLE);
            mtvNote = null;
        }
        if(owner != null){
            mtvCalendarOwner.setText(owner);
            if(owner_name != null){
                mtvCalendarName.setText(owner_name);
            } else{
                mtvCalendarName.setText("None");
            }
        } else{
            mclOwnerLayout.setVisibility(View.GONE);
            mtvCalendarOwner = null;
        }
    }
}
