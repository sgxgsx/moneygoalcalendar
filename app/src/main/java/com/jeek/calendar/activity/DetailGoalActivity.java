package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.DetailGoalAdapter;
import com.jeek.calendar.adapter.GoalsAdapter;
import com.jeek.calendar.task.goal.DeleteGoalTask;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.GoalDatabase.Goal;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailGoalActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String GOAL_OBJ = "GOAL.Obj";
    public static final String GOAL_OBJW = "GOAL.Objw";

    private Toolbar mToolbar;
    private Context mContext;
    private Goal mGoal;
    private RecyclerView rvDetail;
    private DetailGoalAdapter mDetailGoalAdapter;
    private TextView goalName, time;
    private ConstraintLayout dateLayout;

    // TODO VLAD сделать DetailGoalActivity
    // completed VLAD удаление Goal
    // TODO VLAD модифицирование Goal -> Создать GoalEditActivity
    // completed VLAD решить проблему прокрутки
    // TODO VLAD добавление Goal -> Создать GoalAddActivity
    // completed VLAD поиграться с лейаутом (UI)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_goal);
        mContext = getApplicationContext();
        rvDetail = findViewById(R.id.rvAimsEventsGoalDetailActivity);
        mToolbar = findViewById(R.id.tbDetailGoalActivity);
        mToolbar.inflateMenu(R.menu.menu_detail_goal);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.MenuDeleteGoal:
                        deleteGoal();
                        break;
                    case R.id.MenuUpdateGoal:
                        updateGoal();
                        break;
                }
                return false;
            }
        });
        findViewById(R.id.ivCancel).setOnClickListener(this);
        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rvDetail.setLayoutManager(manager);
            DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setSupportsChangeAnimations(false);
            rvDetail.setItemAnimator(itemAnimator);
            mDetailGoalAdapter = new DetailGoalAdapter(this, mGoal);
            rvDetail.setAdapter(mDetailGoalAdapter);
            initUI();
        }


    }


    private void initUI(){
        goalName = findViewById(R.id.tvGoalName);
        time = findViewById(R.id.tvDeadlineGoal);
        dateLayout = findViewById(R.id.DateLayout);
        goalName.setText(mGoal.getGoal_name());
        long ldate = mGoal.getDate_to();
        if(ldate == 0){
            dateLayout.setVisibility(View.GONE);
        } else {
            Date date = new Date(ldate);
            Format format = new SimpleDateFormat("dd.mm.yyyy");
            time.setText(format.format(date));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivCancel:
                finish();
        }
    }

    private void deleteGoal(){
        Toast.makeText(mContext, "Text", Toast.LENGTH_LONG).show();
        new DeleteGoalTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finish();
    }

    private void updateGoal(){
        Intent intent = new Intent(mContext, EditGoalActivity.class).putExtra(DetailGoalActivity.GOAL_OBJ, mGoal);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJW);
                mDetailGoalAdapter.changeAllData(mGoal);
                initUI();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                ; // nothing
                Log.wtf("r", "canceled");
            }
        }
    }
}
