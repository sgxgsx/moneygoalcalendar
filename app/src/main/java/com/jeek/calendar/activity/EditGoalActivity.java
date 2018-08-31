package com.jeek.calendar.activity;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jeek.calendar.R;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jeek.calendar.task.goal.UpdateGoalTask;
import com.jimmy.common.GoalDatabase.Goal;

public class EditGoalActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String GOAL_OBJ = "GOAL.Obj";
    private Goal mGoal;
    private EditText title, description;
    private CheckBox aimsevents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            initUI();
        }


    }

    private void initUI(){
        title = findViewById(R.id.etNameGoal);
        description = findViewById(R.id.etDescription);
        aimsevents = findViewById(R.id.cbAimEvent);
        findViewById(R.id.tvSaveGoal).setOnClickListener(this);
        findViewById(R.id.tvDeadlineGoal).setOnClickListener(this);
        findViewById(R.id.ivChangeColor).setOnClickListener(this);
        findViewById(R.id.ivCancel).setOnClickListener(this);
        title.setText(mGoal.getGoal_name());
        aimsevents.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSaveGoal:
                changeGoal();
            case R.id.tvDeadlineGoal:
                changeTime();
            case R.id.ivChangeColor:
                changeColor();
            case R.id.ivCancel:
                cancel();
        }
    }

    private void changeGoal(){
        mGoal.setGoal_name(title.getText().toString());
        new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Intent returnIntent = new Intent().putExtra(DetailGoalActivity.GOAL_OBJW, mGoal);
        setResult(Activity.RESULT_OK,returnIntent);
        Log.wtf("s", mGoal.getGoal_name());
        Log.wtf("s", title.getText().toString());
        Log.wtf("Sent", "sent");
        finish();
    }

    private void cancel(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void changeTime(){
        ;
    }

    private void changeColor(){

    }
}
