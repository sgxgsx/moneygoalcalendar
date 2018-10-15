package com.jeek.calendar.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.base.app.BaseActivity;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class AddEventDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "Sample";
    //TODO найти способ вызвать метод getsupportfragment из диалога
BaseActivity activity;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";

    private OnAddEventListener mOnAddEventListener;
    private String mColor;
    Schedule mSchedule;
    int EventDay,EventMonth,EventYear;
    Long EventStartTime,EventEndTime;
    EditText EventDesc,EventTitle;
    TextView EventLocation;
    private TextView textView;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    public AddEventDialog(Context context, OnAddEventListener OnAddEventListener,Bundle savedInstanceState) {
        super(context, R.style.DialogFullScreen);
        mOnAddEventListener = OnAddEventListener;
        setContentView(R.layout.dialog_add_event);
        initView();
        initChooseTime(savedInstanceState);
    }

    private void initView() {
        findViewById(R.id.clEventDesc).setOnClickListener(this);
        EventDesc = findViewById(R.id.etScheduleDesc2);
        findViewById(R.id.tvStartTime).setOnClickListener(this);
        findViewById(R.id.tvEndTime).setOnClickListener(this);
        findViewById(R.id.clEventLocation).setOnClickListener(this);
        EventLocation = findViewById(R.id.tvScheduleLocation2);
        findViewById(R.id.tvCreate).setOnClickListener(this);
        findViewById(R.id.tvCancel).setOnClickListener(this);
        EventTitle = findViewById(R.id.etEventTitle);
    }

    private void initChooseTime(Bundle savedInstanceState) {
        textView = findViewById(R.id.tvStartTime);
        if (savedInstanceState != null) {
            // Restore value from saved state
            textView.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
        }

        // Construct SwitchDateTimePicker
        dateTimeFragment = (SwitchDateTimeDialogFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    activity.getString(R.string.label_datetime_dialog),
                    activity.getString(android.R.string.ok),
                    activity.getString(android.R.string.cancel),
                    activity.getString(R.string.clean) // Optional
            );
        }

        // Optionally define a timezone
        dateTimeFragment.setTimeZone(TimeZone.getDefault());

        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(true);
        dateTimeFragment.setHighlightAMPMSelection(true);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                textView.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                textView.setText("");
            }
        });

        TextView textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Re-init each time
                /*dateTimeFragment.startAtCalendarView();*/
                dateTimeFragment.startAtTimeView();
                dateTimeFragment.setDefaultDateTime(new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE)).getTime());
                dateTimeFragment.show(activity.getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCreate:
                createEvent();
                break;
            case R.id.tvCancel:

                break;
            case R.id.clEventLocation:

                break;
            case R.id.clEventDesc:

                break;
            case R.id.tvStartTime:

                break;
            case R.id.tvEndTime:

                break;
        }
    }

    public void createEvent(){
        mSchedule.setDesc(EventDesc.getText().toString());
        mSchedule.setLocation(EventLocation.getText().toString());
        mSchedule.setTitle(EventTitle.getText().toString());
        mSchedule.setDay(EventDay);
        mSchedule.setMonth(EventMonth);
        mSchedule.setYear(EventYear);
        mSchedule.setTime(EventStartTime);
        mSchedule.setTime_end(EventEndTime);
    }


    public interface OnAddEventListener {
        void OnAddEvent(String color);
    }



}
