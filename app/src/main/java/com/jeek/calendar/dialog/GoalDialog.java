package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.DetailGoalActivity;
import com.jeek.calendar.activity.EditGoalActivity;
import com.jeek.calendar.task.goal.DeleteGoalTask;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Goal;

public class GoalDialog extends Dialog implements View.OnClickListener {
    private Goal mGoal;
    private Context mContext;

    public GoalDialog(Context context, Goal goal) {
        super(context, R.style.DialogFullScreen);
        setContentView(R.layout.dialog_goal);
        mGoal = goal;
        mContext = context;

        findViewById(R.id.dsc_archive).setOnClickListener(this);
        findViewById(R.id.dsc_edit).setOnClickListener(this);
        findViewById(R.id.dsc_delete).setOnClickListener(this);
        findViewById(R.id.dsc_settings).setOnClickListener(this);
        findViewById(R.id.dsc_members).setOnClickListener(this);
        findViewById(R.id.dsc_share).setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.dsc_archive:
                archive();
                break;
            case R.id.dsc_edit:
                edit();
                break;
            case R.id.dsc_delete:
                delete();
                break;
            case R.id.dsc_settings:
                gotoSettings();
                break;
            case R.id.dsc_members:
                gotoMembers();
                break;
            case R.id.dsc_share:
                share();
                break;
        }
    }

    private void gotoSettings(){
        //TODO go to DetailGoal -> open slide menu -> open settings.
        Intent intent = new Intent(mContext, DetailGoalActivity.class).putExtra(DetailGoalActivity.GOAL_OBJ, mGoal);
        mContext.startActivity(intent);
        dismiss();
    }

    private void gotoMembers(){
        ;
        dismiss();
    }

    private void delete(){
        new DeleteGoalTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        dismiss();
    }

    private void edit(){
        Intent intent = new Intent(mContext, EditGoalActivity.class).putExtra(DetailGoalActivity.GOAL_OBJ, mGoal);
        mContext.startActivity(intent);
        dismiss();
    }

    private void archive(){
        // Archive or UnArchive
        mGoal.setState(!mGoal.isState());
        new UpdateGoalAsyncTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        dismiss();
    }

    private void share(){

        dismiss();
    }

}
