package com.jeek.calendar.task.aim;

import android.content.Context;
import android.os.AsyncTask;

import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalDatabase;

public class DeleteAimTask extends AsyncTask<Goal, Void, Void> {

    protected Context mContext;
    private Goal goal;
    private int id;

    public DeleteAimTask(Context context, Goal newgoal, Aim aim) {
        mContext = context;
        goal = newgoal;
        id = aim.getId();
    }

    @Override
    protected Void doInBackground(Goal... goals) {
        GoalDatabase.getInstance(mContext).goalDao().updateSettingsCalendar(goal);
        return null;
    }
}