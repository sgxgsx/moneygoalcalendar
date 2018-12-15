/*
package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.task.schedule.GetCalendarInfoTask;
import com.jimmy.common.CalendarSystemDatabase.ScheduleDao;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class TimeChoiceDialog extends Dialog{
    private OnSelectTimeListener mOnSelectTimeListener;

    public TimeChoiceDialog(Context context, TimeChoiceDialog.OnSelectTimeListener OnSelectTimeListener) {

        super(context, R.style.DialogFullScreen);
        mOnSelectTimeListener=OnSelectTimeListener;
        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Title example",
                "OK",
                "Cancel"
        );

        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(2017, Calendar.MARCH, 4, 15, 20).getTime());

        try {
            dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }


        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                // Date is get on positive button click
                // Do something
                mOnSelectTimeListener.onSelectTime();
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });

        initView();
    }
    private void initView() {


    }

    public interface OnSelectTimeListener {
        void onSelectTime(int id,String name);
    }



}
*/
