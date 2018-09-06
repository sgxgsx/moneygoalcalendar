package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeek.calendar.R;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;

import java.util.ArrayList;
import java.util.List;

public class AddAimActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    public static final String GOAL_OBJ = "GOAL.Obj.Add.Aim";
    public static final int errorCode = 200;
    private Goal mGoal;
    private EditText title, description;
    private TextView time;
    private CheckBox cbtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aim);
        title = findViewById(R.id.etNameGoal);
        description = findViewById(R.id.etDescription);
        time = findViewById(R.id.tvDeadlineGoal);
        cbtime = findViewById(R.id.cbTime);

        cbtime.setOnCheckedChangeListener(this);
        findViewById(R.id.ivCancel).setOnClickListener(this);
        findViewById(R.id.tvSaveGoal).setOnClickListener(this);
        findViewById(R.id.ivChangeColor).setOnClickListener(this);
        time.setOnClickListener(this);


        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);

        } else{
            if(mGoal == null) throwError();
        }
    }


    // TODO LEHA добавь выбор времени в этот файл. (который будет вовзращать long)?



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivCancel:
                cancel();
                break;
            case R.id.tvSaveGoal:
                saveAim();
                break;
            case R.id.ivChangeColor:
                changeColor();
                break;
            case R.id.tvDeadlineGoal:
                changeTime();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id){
            case R.id.cbTime:
                showTime();
                break;
        }
    }

    private void saveAim(){
        String name = title.getText().toString();
        String description = title.getText().toString();
        //TODO LEHA тут возвращаешь время. сюда. Мб просто Toast.

        long date_to = 0;
        if(!cbtime.isChecked()){
            date_to = 2040200150;
        }
        //TODO add description to goals
        List<GoalSchedule> goalScheduleList = new ArrayList<>();
        Aim aim = new Aim(name, false, goalScheduleList);
        mGoal.addAim(aim);
        new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finishOkResult();
    }

    private void changeColor(){
        // TODO add changeColor
        ;
    }



    private void changeTime(){
        if(!cbtime.isChecked()){
            Toast.makeText(getApplicationContext(), "unchecked", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Change time", Toast.LENGTH_LONG).show();
    }

    private void showTime(){
        if(cbtime.isChecked()){
            time.setVisibility(View.VISIBLE);
        } else {
            time.setVisibility(View.INVISIBLE);
        }
    }

    private void cancel(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void throwError(){
        Intent returnIntent = new Intent();
        setResult(errorCode, returnIntent);
    }

    private void finishOkResult(){
        Intent returnIntent = new Intent().putExtra(GOAL_OBJ, mGoal);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}
