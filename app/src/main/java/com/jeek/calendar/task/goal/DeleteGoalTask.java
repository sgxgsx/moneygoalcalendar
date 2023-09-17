package com.jeek.calendar.task.goal;

import android.content.Context;
import android.os.AsyncTask;

import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalDatabase;

public class DeleteGoalTask extends AsyncTask<Goal, Void, Void> {

    protected Context mContext;
    private Goal goal;

    public DeleteGoalTask(Context context, Goal newgoal) {
        mContext = context;
        goal = newgoal;
    }

    @Override
    protected Void doInBackground(Goal... goals) {
        GoalDatabase.getInstance(mContext).goalDao().deleteSettingsCalendar(goal);
        return null;
    }
}