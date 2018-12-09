package com.jeek.calendar.activity;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.ScheduleAdapter;
import com.jeek.calendar.dialog.InputLocationDialog;
import com.jeek.calendar.dialog.SelectDateDialog;
import com.jeek.calendar.task.schedule.AddEventTask;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.CalendarSystemDatabase.ScheduleDao;
import com.jimmy.common.base.app.BaseActivity;
import com.jimmy.common.listener.OnTaskFinishedListener;
import com.jimmy.common.util.ToastUtils;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.jeek.calendar.activity.AddEventSetActivity.EVENT_SET_OBJ;

public class AddEventActivity extends BaseActivity implements View.OnClickListener,SelectDateDialog.OnSelectDateListener
        , InputLocationDialog.OnLocationBackListener, OnTaskFinishedListener<Schedule>{


    //
    private static final String TAG = "Sample";

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    private TextView textView;

    private SwitchDateTimeDialogFragment dateTimeFragment;
    //



    public static int CREATE_SCHEDULE_CANCEL = 1;
    public static int CREATE_SCHEDULE_FINISH = 2;
    public static String SCHEDULE_OBJ = "schedule.obj";
    public static String CALENDAR_POSITION = "calendar.position";
    private View vScheduleColor;
    private EditText etScheduleTitle, etScheduleDesc;
    private RelativeLayout rLNoTask;


    private ImageView ivScheduleEventSetIcon;

    private TextView
            tvScheduleTime, tvScheduleLocation;


    private ScheduleDao mScheduleDao;
    private SelectDateDialog mSelectDateDialog;
    private InputLocationDialog mInputLocationDialog;
    private ScheduleAdapter mScheduleAdapter;
    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;
    private long mTime;

    protected Activity mActivity;

    private Schedule mSchedule = new Schedule();
    private int mPosition = -1, MY_CAL_WRITE_REQ=1;



    @Override
    protected void bindView() {
        setContentView(R.layout.activity_add_event_3);
        TextView tvTitle = searchViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.schedule_event_detail_setting));
        searchViewById(R.id.tvCancel).setOnClickListener(this);
        searchViewById(R.id.tvCreate).setOnClickListener(this);
        searchViewById(R.id.llScheduleTime).setOnClickListener(this);
        searchViewById(R.id.llScheduleLocation).setOnClickListener(this);

        /*vScheduleColor = searchViewById(R.id.vScheduleColor);*/
        etScheduleTitle = searchViewById(R.id.etScheduleTitle);
        etScheduleDesc = searchViewById(R.id.etScheduleDesc);
        tvScheduleTime = searchViewById(R.id.tvScheduleTime);
        tvScheduleLocation = searchViewById(R.id.tvScheduleLocation);
    }




    private  void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        mCurrentSelectYear = calendar.get(Calendar.YEAR);
        mCurrentSelectMonth = calendar.get(Calendar.MONTH);
        mCurrentSelectDay = calendar.get(Calendar.DAY_OF_MONTH);
    }


    private void resetDateTimeUi() {
        tvScheduleTime.setText(String.format(getString(R.string.date_format_no_time), mCurrentSelectYear, mCurrentSelectMonth + 1, mCurrentSelectDay));
    }

    @Override
    protected void bindData() {
        super.bindData();
        /*setScheduleData();*/
        setCurrentDate();
        resetDateTimeUi();
        setTimeDialog();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                setResult(CREATE_SCHEDULE_CANCEL);
                finish();
                break;
            case R.id.tvCreate:
                addEvent();
                break;
            case R.id.llScheduleTime:
                showSelectDateDialog();
                break;
            case R.id.llScheduleLocation:
                showInputLocationDialog();
                break;

        }
    }
    private void showInputLocationDialog() {
        if (mInputLocationDialog == null) {
            mInputLocationDialog = new InputLocationDialog(this, this);
        }
        mInputLocationDialog.show();
    }


    public void addEvent() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 999);
        }
        String content = etScheduleTitle.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShortToast(this, R.string.schedule_input_content_is_no_null);
        } else {
            mSchedule.setTitle(content);
            mSchedule.setState(0);
            mSchedule.setTime(mTime);
            mSchedule.setYear(mCurrentSelectYear);
            mSchedule.setMonth(mCurrentSelectMonth);
            mSchedule.setDay(mCurrentSelectDay);
            new AddEventTask(this, this, mSchedule).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
/*
    private void closeSoftInput() {
        etScheduleTitle.clearFocus();
        DeviceUtils.closeSoftInput(mActivity, etScheduleTitle);
    }*/

    private void showSelectDateDialog() {
        if (mSelectDateDialog == null) {
            mSelectDateDialog = new SelectDateDialog(this, this, mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay, mPosition);
        }
        mSelectDateDialog.show();
    }

    @Override
    public void onSelectDate(int year, int month, int day, long time, int position) {
        mSchedule.setYear(year); mCurrentSelectDay=mSchedule.getYear();
        mSchedule.setMonth(month); mCurrentSelectMonth=mSchedule.getMonth();
        mSchedule.setDay(day); mCurrentSelectDay=mSchedule.getDay();
        mSchedule.setTime(time);
        mPosition = position;
        resetDateTimeUi();

    }

    public void setTimeDialog(){
        TextView textView;
        textView = findViewById(R.id.tvEndTime);


        // Construct SwitchDateTimePicker
        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel),
                    getString(R.string.clean) // Optional
            );
        }

        // Optionally define a timezone
        dateTimeFragment.setTimeZone(TimeZone.getDefault());

        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(true);
        dateTimeFragment.setHighlightAMPMSelection(false);
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
        final TextView tv = textView;
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                tv.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                tv.setText("");
            }
        });

        TextView buttonView = findViewById(R.id.tvEndTime);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Re-init each time
                dateTimeFragment.startAtTimeView();
                dateTimeFragment.setDefaultDateTime(new GregorianCalendar(2017, Calendar.MARCH, 4, 15, 20).getTime());
                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });
    }




    @Override
    public void onLocationBack(String text) {
        mSchedule.setLocation(text);
    }
    @Override
    public void onTaskFinished(Schedule data) {
        setResult(1, new Intent().putExtra(EVENT_SET_OBJ, data));
        finish();
    }
}