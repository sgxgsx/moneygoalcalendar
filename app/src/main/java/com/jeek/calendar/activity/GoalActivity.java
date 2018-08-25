package com.jeek.calendar.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.CalendarClassAdapter;
import com.jeek.calendar.adapter.GoalsAdapter;
import com.jeek.calendar.dialog.ConfirmDialog;
import com.jeek.calendar.task.CalendarSettingsEntry.LoadAllCalendarSettingsEntryTask;
import com.jeek.calendar.task.goal.LoadAllGoalsTask;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;
import com.jimmy.common.base.app.BaseActivity;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.ArrayList;
import java.util.List;

public class GoalActivity extends BaseActivity implements View.OnClickListener, OnTaskFinishedListener<List<Goal>>{

    private Toolbar mToolbar;
    private ImageButton mibMenu;
    private RecyclerView rvGoals;
    private GoalsAdapter mGoalsAdapter;
    private List<Goal> mGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_goal);
        mToolbar = findViewById(R.id.GoalsTitleBar);
        rvGoals = findViewById(R.id.rvGoalsGoalActivity);
        findViewById(R.id.ivMenuInGoal).setOnClickListener(this);
        initGoalsList();
    }

    private void initGoalsList() {
        mGoals = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGoals.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvGoals.setItemAnimator(itemAnimator);
        mGoalsAdapter = new GoalsAdapter(this, mGoals);
        rvGoals.setAdapter(mGoalsAdapter);
    }
    @Override
    protected void initData() {
        super.initData();
        new LoadAllGoalsTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onTaskFinished(List<Goal> data) {
        mGoalsAdapter.changeAllData(data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.ivMenuInGoal:
                showMenu();
        }
    }

    private void showMenu(){
        ;
    }
}
