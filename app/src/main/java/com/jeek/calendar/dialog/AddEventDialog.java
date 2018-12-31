package com.jeek.calendar.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jeek.calendar.R;
import com.jeek.calendar.task.schedule.AddEventTask;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
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



public class AddEventDialog extends Dialog implements View.OnClickListener, OnTaskFinishedListener<Schedule> {

    private static final String TAG = "Sample";

    public Activity mActivity;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    Calendar dateAndTime=Calendar.getInstance();
    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    Context mContext1 =this.getContext();
    Context mContext;
    private OnAddEventListener mOnAddEventListener;
    private String mColor;
    Schedule mSchedule=new Schedule();
    EditText EventDesc,EventTitle;
    TextView EventLocation,Title;
    private TextView textView;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    public AddEventDialog(Context context, OnAddEventListener OnAddEventListener,Bundle savedInstanceState) {
        super(context, R.style.DialogFullScreen);
        mContext=context;
        mOnAddEventListener = OnAddEventListener;
        setContentView(R.layout.dialog_add_event);
        initView();
    }

    private void initView() {
        findViewById(R.id.clEventDesc).setOnClickListener(this);
        EventDesc = findViewById(R.id.etScheduleDesc2);
        findViewById(R.id.clEventLocation).setOnClickListener(this);
        EventLocation = findViewById(R.id.tvScheduleLocation2);
        findViewById(R.id.tvCreate).setOnClickListener(this);
        findViewById(R.id.tvCancel).setOnClickListener(this);
        EventTitle = findViewById(R.id.etEventTitle);
        findViewById(R.id.tvDateStart).setOnClickListener(this);
        findViewById(R.id.tvDateEnd).setOnClickListener(this);
        findViewById(R.id.tvTimeStart).setOnClickListener(this);
        findViewById(R.id.tvTimeEnd).setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCreate:
                createEvent();
                break;
            case R.id.tvCancel:
                this.dismiss();
                break;
            case R.id.clEventLocation:

                break;
            case R.id.clEventDesc:

                break;
            case R.id.tvDateStart:
                setDateStartD();
                break;
            case R.id.tvDateEnd:
                setDateEndD();
                break;
            case R.id.tvTimeStart:
                setTimeStart();
                break;
            case R.id.tvTimeEnd:
                setTimeEnd();
                break;
        }
    }

    public void createEvent(){
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_CALENDAR}, 999);
        }
        String content = EventTitle.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShortToast(mContext, R.string.schedule_input_content_is_no_null);
        } else {
            mSchedule.setTitle(EventTitle.getText().toString());
            mSchedule.setState(0);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, mSchedule.getYear());
            cal.set(Calendar.MONTH, mSchedule.getMonth());
            cal.set(Calendar.DAY_OF_MONTH, mSchedule.getDay());
            cal.set(Calendar.HOUR_OF_DAY, mSchedule.getHour());
            cal.set(Calendar.MINUTE, mSchedule.getMinute());
            mSchedule.setTime(cal.getTime().getTime());
            cal.set(Calendar.YEAR, mSchedule.getYearend());
            cal.set(Calendar.MONTH, mSchedule.getMonthend());
            cal.set(Calendar.DAY_OF_MONTH, mSchedule.getDayend());
            cal.set(Calendar.HOUR_OF_DAY, mSchedule.getHourend());
            cal.set(Calendar.MINUTE, mSchedule.getMinuteend());
            mSchedule.setTime_end(cal.getTime().getTime());
            new AddEventTask(mContext, this, mSchedule).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void setDateStartD() {
        new DatePickerDialog(mContext1,
                R.style.MyDatePickerDialogStyle,
                dstart,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    public void setDateEndD() {
        new DatePickerDialog(mContext1,
                R.style.MyDatePickerDialogStyle,
                dend,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTimeStart() {
        new TimePickerDialog(mContext1, tstart,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    public void setTimeEnd() {
        new TimePickerDialog(mContext1, tend,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    private void setInitialDateTimeDateStart() {

        TextView tvv = findViewById(R.id.tvDateStart);
        tvv.setText(DateUtils.formatDateTime(mContext1,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
    private void setInitialDateTimeDateEnd() {

        TextView tvv = findViewById(R.id.tvDateEnd);

        tvv.setText(DateUtils.formatDateTime(mContext1,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
    private void setInitialDateTimeTimeStart() {

        TextView tvv = findViewById(R.id.tvTimeStart);
        tvv.setText(DateUtils.formatDateTime(mContext1,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }
    private void setInitialDateTimeTimeEnd() {

        TextView tvv = findViewById(R.id.tvTimeEnd);
        tvv.setText(DateUtils.formatDateTime(mContext1,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }




    TimePickerDialog.OnTimeSetListener tstart=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);mSchedule.setHour(hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);mSchedule.setMinute(minute);
            setInitialDateTimeTimeStart();
        }
    };
    TimePickerDialog.OnTimeSetListener tend=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);mSchedule.setHourend(hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);mSchedule.setMinuteend(minute);
            setInitialDateTimeTimeEnd();
        }
    };

    DatePickerDialog.OnDateSetListener dstart=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);mSchedule.setYear(year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);mSchedule.setMonth(monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);mSchedule.setDay(dayOfMonth);
            setInitialDateTimeDateStart();
        }
    };
    DatePickerDialog.OnDateSetListener dend=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);mSchedule.setYearend(year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);mSchedule.setMonthend(monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);mSchedule.setDayend(dayOfMonth);
            setInitialDateTimeDateEnd();
        }
    };

    public interface OnAddEventListener {
        void OnAddEvent(String color);
    }

    public void onTaskFinished(Schedule data) {
        //mActivity.setResult(1, new Intent().putExtra(EVENT_SET_OBJ, data));
        //finish();
        this.dismiss();
    }

}
