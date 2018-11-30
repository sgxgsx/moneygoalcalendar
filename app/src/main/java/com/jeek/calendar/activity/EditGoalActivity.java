package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeek.calendar.R;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Goal;

public class EditGoalActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    public static final String GOAL_OBJ = "GOAL.Obj";
    private Goal mGoal;
    private EditText title, description;
    private TextView time;
    private CheckBox aimsevents, cbtime;

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
        time = findViewById(R.id.tvDeadlineGoal);
        cbtime = findViewById(R.id.cbTime);

        cbtime.setOnCheckedChangeListener(this);
        findViewById(R.id.llSaveGoal).setOnClickListener(this);
        findViewById(R.id.tvDeadlineGoal).setOnClickListener(this);
        findViewById(R.id.ivChangeColor).setOnClickListener(this);
        findViewById(R.id.ivCancel).setOnClickListener(this);
        description.setText(mGoal.getDescription());
        title.setText(mGoal.getGoal_name());
        aimsevents.setChecked(true);
        time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llSaveGoal:
                changeGoal();
                break;
            case R.id.tvDeadlineGoal:
                changeTime();
                break;
            case R.id.ivChangeColor:
                changeColor();
                break;
            case R.id.ivCancel:
                cancel();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cbTime:
                checkTime();
                break;
        }
    }

    private void changeGoal(){
        mGoal.setGoal_name(title.getText().toString());
        mGoal.setDescription(description.getText().toString());
        if(cbtime.isChecked()){
            mGoal.setDate_to(0);
        }
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

    private void checkTime(){
        if(cbtime.isChecked()){
            time.setVisibility(View.GONE);
        } else{
            time.setVisibility(View.VISIBLE);
        }
    }

    private void changeTime(){
        if(!cbtime.isChecked()){
            Toast.makeText(getApplicationContext(), "unchecked", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Change time", Toast.LENGTH_LONG).show();
    }

    private void changeColor(){

    }
}
