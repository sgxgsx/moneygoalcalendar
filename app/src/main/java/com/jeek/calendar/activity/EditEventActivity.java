package com.jeek.calendar.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jeek.calendar.R;
import com.jeek.calendar.task.schedule.EditEventTask;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.base.app.BaseActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditEventActivity extends BaseActivity implements View.OnClickListener {

    TextView tvDateStart, tvDateEnd, tvTimeStart, tvTimeEnd, tvAccName, tvSaveGoal;
    EditText etScheduleTitle, etScheduleDesc;
    Schedule mSchedule = new Schedule();
    ConstraintLayout llAddEvent;
    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void bindView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        Log.wtf("Extras", extras.getString("Title"));
        setContentView(R.layout.activity_add_event_3);
        initView(extras);
    }


    private void initView(Bundle extras) {
        setScheduleObjData(extras);
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date dateStart = new Date(mSchedule.getTime());
        Date dateEnd = new Date(mSchedule.getTime_end());
        calStart.setTime(dateStart);
        calEnd.setTime(dateEnd);


        etScheduleTitle = searchViewById(R.id.etScheduleTitle);
        etScheduleDesc = searchViewById(R.id.etScheduleDesc);
        tvDateStart = searchViewById(R.id.tvDateStart);
        tvDateEnd = searchViewById(R.id.tvDateEnd);
        tvTimeStart = searchViewById(R.id.tvTimeStart);
        tvTimeEnd = searchViewById(R.id.tvTimeEnd);
        tvSaveGoal = searchViewById(R.id.tvSaveGoal);
        searchViewById(R.id.tvSaveGoal).setOnClickListener(this);
        tvAccName = searchViewById(R.id.tvCalendarOwnerTextView);
        searchViewById(R.id.tvSaveGoal).setOnClickListener(this);

        etScheduleTitle.setText(extras.getString("Title"));
        etScheduleDesc.setText(extras.getString("Description"));
        tvDateStart.setText(mSchedule.getYear() + " " + calStart.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + mSchedule.getDay());
        //tvDateEnd.setText(mSchedule.getYearend() + " " + calEnd.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + mSchedule.getDayend());
        /*tvTimeStart.setText(DateUtils.formatDateTime(this,
                calStart.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
        tvTimeEnd.setText(DateUtils.formatDateTime(this,
                calEnd.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
*/      resetDateTimeUi();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSaveGoal:
                Log.wtf("SaveChanges", "OnClick");
                editEvent();
                break;
            case R.id.tvCalendarOwnerTextView:

                break;
            default:
                break;
        }
    }
    private void resetDateTimeUi() {
        tvDateStart.setText(String.format(getString(R.string.date_format_no_time), mSchedule.getYear(), mSchedule.getMonth()+ 1, mSchedule.getDay()));
        tvTimeStart.setText(String.format("%02d", mSchedule.getHour()) + " : " + String.format("%02d", mSchedule.getMinute()));

        if (mSchedule.getHour() != 23) {
            tvDateEnd.setText(String.format(getString(R.string.date_format_no_time), mSchedule.getYear(), mSchedule.getMonth()+ 1, mSchedule.getDay()));
            tvTimeEnd.setText(String.format("%02d", mSchedule.getHour()+1) + " : " + String.format("%02d", mSchedule.getMinute()));
        } else {
            tvDateEnd.setText(String.format(getString(R.string.date_format_no_time), mSchedule.getYear(), mSchedule.getMonth()+ 1, mSchedule.getDay()+1));
            tvTimeEnd.setText(String.format("%02d", 0) + " : " + String.format("%02d", mSchedule.getMinute()));
        }
    }

    public void editEvent() {
        mSchedule.setTitle(etScheduleTitle.getText().toString());
        mSchedule.setDesc(etScheduleDesc.getText().toString());

        new EditEventTask(this, mSchedule).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setScheduleObjData(Bundle extras) {
        mSchedule.setTitle(extras.getString("Title"));
        mSchedule.setDesc(extras.getString("Description"));
        mSchedule.setAccount(extras.getString("Account"));
        mSchedule.setAccount_name(extras.getString("Account_name"));
        mSchedule.setLocation(extras.getString("Location"));
        mSchedule.setRepeat(extras.getString("Repeat"));
        mSchedule.setState(extras.getInt("State"));
        mSchedule.setCalID(extras.getInt("Id"));
        mSchedule.setColor(extras.getInt("Color"));
        mSchedule.setMonth(extras.getInt("Month"));
        mSchedule.setMonthend(extras.getInt("Monthend"));
        mSchedule.setMinute(extras.getInt("Minute"));
        mSchedule.setHour(extras.getInt("Hour"));
        mSchedule.setDay(extras.getInt("Day"));
        mSchedule.setDayend(extras.getInt("Dayend"));
        mSchedule.setHourend(extras.getInt("Hourend"));
        mSchedule.setMinuteend(extras.getInt("Minuteend"));
        mSchedule.setYear(extras.getInt("Year"));
        mSchedule.setYearend(extras.getInt("Yearend"));
        mSchedule.setTime(extras.getLong("Time"));
        mSchedule.setTime_end(extras.getLong("Timeend"));
    }

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


    TimePickerDialog.OnTimeSetListener tstart = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mSchedule.setHour(hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            mSchedule.setMinute(minute);
            setInitialDateTimeTimeStart();
        }
    };
    TimePickerDialog.OnTimeSetListener tend = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mSchedule.setHourend(hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            mSchedule.setMinuteend(minute);
            setInitialDateTimeTimeEnd();
        }
    };

    DatePickerDialog.OnDateSetListener dstart = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            mSchedule.setYear(year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            mSchedule.setMonth(monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mSchedule.setDay(dayOfMonth);
            setInitialDateTimeDateStart();
        }
    };
    DatePickerDialog.OnDateSetListener dend = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            mSchedule.setYearend(year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            mSchedule.setMonthend(monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mSchedule.setDayend(dayOfMonth);
            setInitialDateTimeDateEnd();
        }
    };


}
