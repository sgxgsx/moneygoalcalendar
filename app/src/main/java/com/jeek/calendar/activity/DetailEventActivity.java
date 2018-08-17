package com.jeek.calendar.activity;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private ConstraintLayout mclTextTitle, mLocationLayout;
    private ScrollView mScrollView;


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
        mclTextTitle = findViewById(R.id.constraintLayout);
        mLocationLayout = findViewById(R.id.constraintLayout3);
        mScrollView = findViewById(R.id.svEventDetail);

        if(getIntent().hasExtra(SCHEDULE_OBJ)){
            mSchedule = (Schedule) getIntent().getSerializableExtra(SCHEDULE_OBJ);

            Log.wtf("COlor", String.valueOf(mSchedule.getColor()));
            Date date = new Date(mSchedule.getTime());
            Format format = new SimpleDateFormat("EEEE, MMMM d, HH:mm");
            Date date_end = new Date(mSchedule.getTime_end());
            Format format_end = new SimpleDateFormat("EEEE, MMMM d, HH:mm");

            mtvStartTime.setText(format.format(date) + " -");
            mtvEndTime.setText(format_end.format(date_end));

            String repeat = mSchedule.getRepeat();
            if(repeat != null){
                mtvRepeat.setText(repeat);
            } else{
                mtvRepeat.setVisibility(View.INVISIBLE);
            }
            mclTextTitle.setBackgroundColor(mSchedule.getColor());

            String location = mSchedule.getLocation();
            if(location != null){
                mtvLocation.setText(location);
            } else{
                mScrollView.removeView(mLocationLayout);
            }
            // TODO VLAD поменять все ConstraintsLayout'ы на LinearLayout'ы и поставить между ними пустые View для разграничения.

        }
    }

}
