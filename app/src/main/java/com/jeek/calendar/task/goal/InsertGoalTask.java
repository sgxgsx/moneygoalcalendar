package com.jeek.calendar.task.goal;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;

public class InsertGoalTask extends AsyncTask<Goal, Void, Void> {

    protected Context mContext;
    private Goal goal;

    public InsertGoalTask(Context context, Goal newgoal) {
        mContext = context;
        goal = newgoal;
    }

    @Override
    protected Void doInBackground(Goal... goals) {
        Log.wtf("inserted", "insert");
        GoalDatabase.getInstance(mContext).goalDao().insertGoal(goal);
        return null;
    }
}