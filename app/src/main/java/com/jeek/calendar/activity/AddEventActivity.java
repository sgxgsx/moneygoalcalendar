package com.jeek.calendar.activity;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class AddEventActivity extends BaseActivity implements View.OnClickListener,SelectDateDialog.OnSelectDateListener
        , InputLocationDialog.OnLocationBackListener, OnTaskFinishedListener<Schedule>{




    public static String EVENT_SET_OBJ = "event.set.obj";
    private static final String TAG = "Sample";

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    //



    public static int CREATE_SCHEDULE_CANCEL = 1;
    public static int CREATE_SCHEDULE_FINISH = 2;
    public static String SCHEDULE_OBJ = "schedule.obj";
    public static String CALENDAR_POSITION = "calendar.position";
    private View vScheduleColor;
    private EditText etScheduleTitle, etScheduleDesc;


    public  int timeDialogId=0;
    public TextView v;
    private TextView tvDateStart,tvDateEnd,tvTimeStart,tvTimeEnd;

    Calendar dateAndTime=Calendar.getInstance();

    private SelectDateDialog mSelectDateDialog;
    private InputLocationDialog mInputLocationDialog;

    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;
    private long mTime;

    protected Activity mActivity;

    private Schedule mSchedule = new Schedule();
    private int mPosition = -1, MY_CAL_WRITE_REQ=1;



    private int startDate, endDate, startTime, endTime;
    //   TODO ПРОВЕРКИ
    /*
            1. При создании активити ставить startDate & endDate - текущую дату, startTime текущее время + недостающее кол-во секунд
               для того что бы приравнять время к целому ( Например мы получили 3500 секунд, то нужно сделать 3600), endTime - на 1 час больше от startTime
            2. При смене даты startDate проверять является ли стартДейт больше чем endDate если да то поставить такую же дату и ЭндДейту
            3. При смене времени делать такую же проверку как и в 2 только на время, и если стартТайм больше ЭндТайм (если Даты одинаковы) то присвоить ЭндТайм такое же
                время как и СтартТайм + 1 час
            4. .....
     */



    @Override
    protected void bindView() {
        setContentView(R.layout.activity_add_event_3);
       /* TextView tvTitle = searchViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.schedule_event_detail_setting));*/
        searchViewById(R.id.llCancel).setOnClickListener(this);
        //searchViewById(R.id.llSaveGoal).setOnClickListener(this);
        searchViewById(R.id.llAddEvent).setOnClickListener(this);
        //searchViewById(R.id.llScheduleTime).setOnClickListener(this);
        //searchViewById(R.id.llScheduleLocation).setOnClickListener(this);
        //searchViewById(R.id.tvScheduleTime3).setOnClickListener(this);

        /*vScheduleColor = searchViewById(R.id.vScheduleColor);*/
        etScheduleTitle = searchViewById(R.id.etScheduleTitle);
        etScheduleDesc = searchViewById(R.id.etScheduleDesc);
        tvDateStart = searchViewById(R.id.tvDateStart);
        tvDateEnd = searchViewById(R.id.tvDateEnd);
        tvTimeStart = searchViewById(R.id.tvTimeStart);
        tvTimeEnd = searchViewById(R.id.tvTimeEnd);
        //tvScheduleLocation = searchViewById(R.id.tvScheduleLocation);

    }




    private  void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        mCurrentSelectYear = calendar.get(Calendar.YEAR);
        mCurrentSelectMonth = calendar.get(Calendar.MONTH);
        mCurrentSelectDay = calendar.get(Calendar.DAY_OF_MONTH);
    }


    private void resetDateTimeUi() {
        tvDateStart.setText(String.format(getString(R.string.date_format_no_time), mCurrentSelectYear, mCurrentSelectMonth + 1, mCurrentSelectDay));
        tvDateEnd.setText(String.format(getString(R.string.date_format_no_time), mCurrentSelectYear, mCurrentSelectMonth + 1, mCurrentSelectDay));
        tvTimeStart.setText("00 : 00");
        tvTimeEnd.setText("00 : 00");

    }

    @Override
    protected void bindData() {
        super.bindData();
        /*setScheduleData();*/
        setCurrentDate();
        resetDateTimeUi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCancel:
                setResult(CREATE_SCHEDULE_CANCEL);
                finish();
                break;
            case R.id.llAddEvent:
                addEvent();
                break;
            /*case R.id.llScheduleTime:
                showSelectDateDialog();
                break;*/
            /*case R.id.llScheduleLocation:
                showInputLocationDialog();
                break;*/


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
        /*mSchedule.setYear(year); mCurrentSelectDay=mSchedule.getYear();
        mSchedule.setMonth(month); mCurrentSelectMonth=mSchedule.getMonth();
        mSchedule.setDay(day); mCurrentSelectDay=mSchedule.getDay();
        mSchedule.setTime(time);*/
        mPosition = position;
        resetDateTimeUi();

    }


    /*public void showDatePicker(View v) {
        MyDatePickerDialog newFragment = new MyDatePickerDialog();
        newFragment.show(getSupportFragmentManager(), "date picker");
        Log.wtf("onClick  ","");
    }*/


    public void setDateStart(View v) {
        new DatePickerDialog(this,
                R.style.MyDatePickerDialogStyle,
                dstart,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    public void setDateEnd(View v) {
        new DatePickerDialog(this,
                R.style.MyDatePickerDialogStyle,
                dend,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    public void setTimeStart(View v) {
        new TimePickerDialog(this, R.style.myTimePickerStyle, tstart,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    public void setTimeEnd(View v) {
        new TimePickerDialog(this, R.style.myTimePickerStyle, tend,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTimeDateStart() {

        TextView tvv = findViewById(R.id.tvDateStart);
                tvv.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
    private void setInitialDateTimeDateEnd() {

        TextView tvv = findViewById(R.id.tvDateEnd);

        tvv.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
    private void setInitialDateTimeTimeStart() {

        TextView tvv = findViewById(R.id.tvTimeStart);
        tvv.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }
    private void setInitialDateTimeTimeEnd() {

        TextView tvv = findViewById(R.id.tvTimeEnd);
        tvv.setText(DateUtils.formatDateTime(this,
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