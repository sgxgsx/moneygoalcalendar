package com.jeek.calendar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.GoalDatabase.Goal;

public class DetailGoalActivity extends AppCompatActivity {
    public static final String GOAL_OBJ = "GOAL.Obj";


    private Toolbar mToolbar;

    private Goal mGoal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_goal);


        mToolbar = findViewById(R.id.tbDetailGoalActivity);
        mToolbar.inflateMenu(R.menu.detail_event_menu);
        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            initUI();
        }
    }

    private void initUI(){
        ;
    }

}
