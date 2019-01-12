package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.content.Context;

import com.jeek.calendar.R;

public class GoalDialog extends Dialog {

    public GoalDialog(Context context) {
        super(context, R.style.DialogFullScreen);
        setContentView(R.layout.dialog_goal);

    }
}
