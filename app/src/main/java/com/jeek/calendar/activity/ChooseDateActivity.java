package com.jeek.calendar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ChooseDateActivity extends AppCompatActivity {


    private static final String TAG = "Sample";

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    private TextView textView;
    private long timeStart, timeEnd;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);

        textView = findViewById(R.id.textView);
        if (savedInstanceState != null) {
            // Restore value from saved state
            textView.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
        }

        // Construct SwitchDateTimePicker
        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if (dateTimeFragment == null) {
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
                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current textView
        savedInstanceState.putCharSequence(STATE_TEXTVIEW, textView.getText());
        super.onSaveInstanceState(savedInstanceState);
    }
}