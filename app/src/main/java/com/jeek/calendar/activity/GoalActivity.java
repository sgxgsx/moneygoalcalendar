package com.jeek.calendar.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.GoalsAdapter;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalDatabase;
import com.jimmy.common.base.app.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class GoalActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private ImageButton mibMenu;
    //private ScrollView svGoals;
    private RecyclerView rvGoals;
    private GoalsAdapter mGoalsAdapter;
    private List<Goal> mGoals;
    private GoalDatabase mGoalDatabase;

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
        findViewById(R.id.fabGoal).setOnClickListener(this);
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
        Log.wtf("tag", "from database");
        mGoalDatabase = GoalDatabase.getInstance(getApplicationContext());
        final LiveData<List<Goal>> goals = mGoalDatabase.goalDao().loadGoals();
        goals.observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(@Nullable List<Goal> goals) {
                Log.wtf("TAG","FROM LIVEDATA");
                mGoalsAdapter.changeAllData(goals);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.ivMenuInGoal:
                showMenu();
                break;
            case R.id.fabGoal:
                addGoal();
                break;
        }
    }

    private void showMenu(){
        ;
    }

    private void addGoal(){
        Intent intent = new Intent(this, AddGoalActivity.class);
        startActivity(intent);
        mGoalsAdapter.notifyDataSetChanged();
    }
}
