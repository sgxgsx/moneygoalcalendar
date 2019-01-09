package com.jeek.calendar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.base.app.BaseActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditEventActivity extends BaseActivity {

    TextView tvDateStart,tvDateEnd, tvTimeStart, tvTimeEnd;
    EditText etScheduleTitle,etScheduleDesc;
    Schedule mSchedule=new Schedule();
    @Override
    protected void bindView(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        Log.wtf("Extras",extras.getString("Title"));
        setContentView(R.layout.activity_add_event_3);
        initView(extras);
    }


    private void initView(Bundle extras){
        mSchedule.setTitle(extras.getString("Title"));
        mSchedule.setDesc(extras.getString("Description"));
        mSchedule.setAccount(extras.getString("Account"));
        mSchedule.setAccount_name(extras.getString("Account_name"));
        mSchedule.setLocation(extras.getString("Location"));
        mSchedule.setRepeat(extras.getString("Repeat"));
        mSchedule.setState(extras.getInt("State"));
        mSchedule.setId(extras.getInt("Id"));
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
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date dateStart =new Date(mSchedule.getTime());
        Date dateEnd = new Date(mSchedule.getTime_end());
        calStart.setTime(dateStart);
        calEnd.setTime(dateEnd);


        etScheduleTitle = searchViewById(R.id.etScheduleTitle);
        etScheduleDesc = searchViewById(R.id.etScheduleDesc);
        tvDateStart = searchViewById(R.id.tvDateStart);
        tvDateEnd = searchViewById(R.id.tvDateEnd);
        tvTimeStart = searchViewById(R.id.tvTimeStart);
        tvTimeEnd = searchViewById(R.id.tvTimeEnd);
        etScheduleTitle.setText(extras.getString("Title"));
        etScheduleDesc.setText(extras.getString("Description"));
        tvDateStart.setText(mSchedule.getYear()+" "+calStart.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.ENGLISH)+" "+mSchedule.getDay());
        tvDateEnd.setText(mSchedule.getYearend()+" "+calEnd.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.ENGLISH)+" "+mSchedule.getDayend());
        tvTimeStart.setText(DateUtils.formatDateTime(this,
                calStart.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
        tvTimeEnd.setText(DateUtils.formatDateTime(this,
                calEnd.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));

    }







}
