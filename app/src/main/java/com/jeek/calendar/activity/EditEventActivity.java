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
        Intent i = getIntent();
        mSchedule = (Schedule) i.getSerializableExtra("mSchedule");
        Bundle extras = getIntent().getExtras();
        Log.wtf("Extras", mSchedule.getTitle());
        setContentView(R.layout.activity_add_event_3);
        initView(extras);
    }


    private void initView(Bundle extras) {
        //setScheduleObjData(extras);
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
        searchViewById(R.id.llCancel).setOnClickListener(this);


        etScheduleTitle.setText(mSchedule.getTitle());
        etScheduleDesc.setText(mSchedule.getDesc());
        //tvDateStart.setText(mSchedule.getYear() + " " + calStart.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + mSchedule.getDay());
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
            case R.id.llCancel:
                finish();
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
        tvv.setText(mSchedule.getYear()+"-"+mSchedule.getMonth()+1+"-"+mSchedule.getDay());
        if (    (mSchedule.getYear() > mSchedule.getYearend()) ||
                (mSchedule.getMonth() > mSchedule.getMonthend() && mSchedule.getYear() >= mSchedule.getYearend()) ||
                (mSchedule.getMonth() >= mSchedule.getMonthend() && mSchedule.getYear() >= mSchedule.getYearend() && mSchedule.getDay() > mSchedule.getDayend())) {
            TextView tv = findViewById(R.id.tvDateEnd);
            tv.setText(mSchedule.getYear() + "-" + mSchedule.getMonth()+1 + "-" + mSchedule.getDay());
            mSchedule.setYearend(mSchedule.getYear());
            mSchedule.setMonthend(mSchedule.getMonth());
            mSchedule.setDayend(mSchedule.getDay());
        }
    }

    private void setInitialDateTimeDateEnd() {
        TextView tvv = findViewById(R.id.tvDateEnd);
        tvv.setText(mSchedule.getYearend() + "-" + mSchedule.getMonthend() + 1 + "-" + mSchedule.getDayend());
        if (    (mSchedule.getYear() > mSchedule.getYearend()) ||
                (mSchedule.getMonth() > mSchedule.getMonthend() && mSchedule.getYear() >= mSchedule.getYearend()) ||
                (mSchedule.getMonth() >= mSchedule.getMonthend() && mSchedule.getYear() >= mSchedule.getYearend() && mSchedule.getDay() > mSchedule.getDayend())) {
            TextView tv = findViewById(R.id.tvDateStart);
            tv.setText(mSchedule.getYearend() + "-" + mSchedule.getMonthend() + 1 + "-" + mSchedule.getDayend());
            mSchedule.setYear(mSchedule.getYearend());
            mSchedule.setMonth(mSchedule.getMonthend());
            mSchedule.setDay(mSchedule.getDayend());
        }
    }

    private void setInitialDateTimeTimeStart() {
        TextView tvv = findViewById(R.id.tvTimeStart);
        tvv.setText(String.format("%02d", mSchedule.getHour()) + " : " + String.format("%02d", mSchedule.getMinute()));
        if ((mSchedule.getHour() > mSchedule.getHourend() && (mSchedule.getDay() == mSchedule.getDayend() && mSchedule.getMonth() == mSchedule.getMonthend())) ||
                (mSchedule.getMinute() > mSchedule.getMinuteend() && mSchedule.getHour() >= mSchedule.getHourend() && (mSchedule.getDay() == mSchedule.getDayend() && mSchedule.getMonth() == mSchedule.getMonthend()))) {
            TextView tv = findViewById(R.id.tvTimeEnd);
            tv.setText(String.format("%02d", mSchedule.getHour()) + " : " + String.format("%02d", mSchedule.getMinute()));
            mSchedule.setHourend(mSchedule.getHour());
            mSchedule.setMinuteend(mSchedule.getMinute());
        }
    }

    private void setInitialDateTimeTimeEnd() {
        TextView tvv = findViewById(R.id.tvTimeEnd);
        tvv.setText(String.format("%02d", mSchedule.getHourend()) + " : " + String.format("%02d", mSchedule.getMinuteend()));
        if ((mSchedule.getHour() > mSchedule.getHourend() && (mSchedule.getDay() == mSchedule.getDayend() && mSchedule.getMonth() == mSchedule.getMonthend())) ||
                (mSchedule.getMinute() > mSchedule.getMinuteend() && mSchedule.getHour() >= mSchedule.getHourend() && (mSchedule.getDay() == mSchedule.getDayend() && mSchedule.getMonth() == mSchedule.getMonthend()))) {
            TextView tv = findViewById(R.id.tvTimeStart);
            tv.setText(String.format("%02d", mSchedule.getHourend()) + " : " + String.format("%02d", mSchedule.getMinuteend()));
            mSchedule.setHour(mSchedule.getHourend());
            mSchedule.setMinute(mSchedule.getMinuteend());
        }
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
