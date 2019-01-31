package com.jeek.calendar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jimmy.common.GoalDatabase.GoalList;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String GOAL_OBJ = "GOAL.Obj";
    private GoalList goalList;
    private Toolbar mToolbar;
    private EditText mTitle, mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mToolbar = findViewById(R.id.tbTaskActivity);
        findViewById(R.id.llCancel).setOnClickListener(this);
        mTitle = findViewById(R.id.etTitle);
        mText = findViewById(R.id.etText);
        if (getIntent().hasExtra(GOAL_OBJ)) {
            goalList = (GoalList) getIntent().getSerializableExtra(GOAL_OBJ);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCancel:
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}