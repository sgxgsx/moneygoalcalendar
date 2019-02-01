package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalList;
import com.jimmy.common.GoalDatabase.Task;
import com.jimmy.common.ItemWrapper;

import java.util.List;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String GOAL_OBJ = "GOAL.Obj";
    public static final String GOALLIST_OBJ = "GOALLIST.Obj";

    private GoalList goalList;
    private Goal mGoal;
    private Task mTask;
    private List<ItemWrapper> mItems;
    private int mPosition;

    private Toolbar mToolbar;
    private EditText mTitle, mDescription;

    private String tTitle, tDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mToolbar = findViewById(R.id.tbTaskActivity);
        findViewById(R.id.llCancel).setOnClickListener(this);
        mTitle = findViewById(R.id.etTitle);
        mDescription = findViewById(R.id.etText);

        try {
            goalList  = (GoalList) getIntent().getSerializableExtra(GOALLIST_OBJ);
            mGoal     = (Goal)     getIntent().getSerializableExtra(GOAL_OBJ);
            mPosition = getIntent().getIntExtra("int", -10);
            if(mPosition == -10) throw new Exception("-10 value");
            mItems = goalList.getItems();
            mTask  = (Task) mItems.get(mPosition);
        } catch (Exception e){
            Log.wtf("EXCEPTION", "EXCEPTION");
            e.printStackTrace();
            finish();
        }
        doInBackground();
        initUI();
    }

    private void initUI(){
        mTitle.setText(mTask.getTitle());
        mDescription.setText(mTask.getText());
    }

    private void doInBackground(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                tTitle = mTask.getTitle();
                tDescription = mTask.getText();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        String title = mTitle.getText().toString();
        String descr = mDescription.getText().toString();

        Log.wtf("ss", tTitle);
        Log.wtf("ww", tDescription);
        if(tTitle != null && tDescription != null && !(title.equals(tTitle) && descr.equals(tDescription))){
            Log.wtf("gut", "gut");
            mGoal.getLists().get(goalList.getId()).getItems().get(mPosition).changeTask(title, descr, 20000);

            Log.wtf("sssss", ((Task) mGoal.getLists().get(goalList.getId()).getItems().get(mPosition)).getTitle());
            Intent returnIntent = new Intent().putExtra(GOAL_OBJ, mGoal).putExtra("int", goalList.getId()).putExtra("int2", mPosition);
            setResult(Activity.RESULT_OK, returnIntent);
        }
        finish();
        Log.wtf("back", "press");
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCancel:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}