package com.jeek.calendar.task.goal;

import android.content.Context;

import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalDatabase;
import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.List;

public class UpdateGoalTask extends BaseAsyncTask<Void> {

    protected Context mContext;
    private Goal goal;

    public UpdateGoalTask(Context context,Goal goal, OnTaskFinishedListener<Void> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        GoalDatabase.getInstance(mContext).goalDao().updateSettingsCalendar(goal);
        return null;
    }
}