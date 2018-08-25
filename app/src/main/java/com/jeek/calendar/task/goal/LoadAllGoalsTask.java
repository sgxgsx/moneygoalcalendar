package com.jeek.calendar.task.goal;

import android.content.Context;
import android.os.AsyncTask;

import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalDatabase;
import com.jimmy.common.base.task.BaseAsyncTask;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.List;


public class LoadAllGoalsTask extends BaseAsyncTask<List<Goal>> {

    protected Context mContext;

    public LoadAllGoalsTask(Context context, OnTaskFinishedListener<List<Goal>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected List<Goal> doInBackground(Void... voids) {
        return GoalDatabase.getInstance(mContext).goalDao().loadGoals();
    }
}