package com.jeek.calendar.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.GoalsAdapter;
import com.jeek.calendar.dialog.ConfirmDialog;
import com.jeek.calendar.task.goal.InsertGoalTask;
import com.jimmy.common.GoalDatabase.Goal;

public class AddGoalActivity extends AppCompatActivity implements View.OnClickListener{

    private GoalsAdapter mGoalsAdapter;

    private EditText title, description;
    private CheckBox aimsevents;



    // TODO сделать лейаут AddGoalActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        title = findViewById(R.id.etNameGoal);
        description = findViewById(R.id.etDescription);
        aimsevents = findViewById(R.id.cbAimEvent);

        findViewById(R.id.ivCancel).setOnClickListener(this);
        findViewById(R.id.tvSaveGoal).setOnClickListener(this);
        findViewById(R.id.ivChangeColor).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivCancel:
                finish();
            case R.id.tvSaveGoal:
                saveGoal();
            case R.id.ivChangeColor:
                changeColor();
        }
    }


    private void saveGoal(){
        String name = title.getText().toString();
        String description = title.getText().toString();
        boolean aimandevents = aimsevents.isChecked();
        //TODO add description to goals
        Goal goal = new Goal(name,0, null, null);
        new InsertGoalTask(getApplicationContext(), goal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finish();
    }

    private void changeColor(){
        // TODO add changeColor
        ;
    }
}
