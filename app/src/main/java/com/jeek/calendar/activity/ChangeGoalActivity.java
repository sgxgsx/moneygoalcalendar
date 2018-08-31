package com.jeek.calendar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jimmy.common.GoalDatabase.Goal;

public class ChangeGoalActivity extends AppCompatActivity implements View.OnClickListener{
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
        }
    }

    private void changeGoal(){
        ;
    }

    private void changeTime(){
        ;
    }

    private void changeColor(){

    }
}
