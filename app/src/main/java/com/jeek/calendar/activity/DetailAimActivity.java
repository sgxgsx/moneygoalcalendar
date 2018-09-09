package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.DetailAimAdapter;
import com.jeek.calendar.adapter.DetailGoalAdapter;
import com.jeek.calendar.task.aim.DeleteAimTask;
import com.jeek.calendar.task.goal.DeleteGoalTask;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jeek.calendar.task.goal.UpdateGoalTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailAimActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String GOAL_OBJ = "GOAL.Obj.Detail.Aim";
    public static final String AIM_OBJ = "AIM.Obj.Detail.Aim";
    public static final int errorCode = 200;

    private boolean mChanges = false;

    private Toolbar mToolbar;
    private Context mContext;
    private Goal mGoal;
    private Aim mAim;
    private RecyclerView rvDetail;
    private DetailAimAdapter mDetailAimAdapter;
    private TextView aimName, time, description;
    private ConstraintLayout dateLayout;
    private ImageView noteImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aim);
        mContext = getApplicationContext();
        rvDetail = findViewById(R.id.rvAimsEventsAimsDetailActivity);
        mToolbar = findViewById(R.id.tbDetailAimActivity);
        mToolbar.inflateMenu(R.menu.menu_detail_goal);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.MenuDeleteGoal:
                        deleteAim();
                        break;
                    case R.id.MenuUpdateGoal:
                        updateAim();
                        break;
                }
                return false;
            }
        });
        findViewById(R.id.ivCancel).setOnClickListener(this);
        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            mAim = (Aim) getIntent().getSerializableExtra(AIM_OBJ);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rvDetail.setLayoutManager(manager);
            DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setSupportsChangeAnimations(false);
            rvDetail.setItemAnimator(itemAnimator);
            mDetailAimAdapter = new DetailAimAdapter(this, mGoal, mAim);
            rvDetail.setAdapter(mDetailAimAdapter);
            initUI();
        }


    }


    private void initUI(){
        aimName = findViewById(R.id.tvAimName);
        time = findViewById(R.id.tvDeadlineAim);
        dateLayout = findViewById(R.id.DateLayout);
        description = findViewById(R.id.tvNoteTextView);

        noteImage = findViewById(R.id.iNoteImage);
        //TODO ПОМЕНЯТЬ INT ЦВЕТА
        noteImage.setColorFilter(-525666, PorterDuff.Mode.MULTIPLY);
        aimName.setText(mAim.getName());
        //description.setText(mAim.getScheduleList().toString());
        description.setText(mAim.getDescription());
        //long ldate = mAim.getDate_to();
        long ldate = 0;
        if(ldate == 0){
            dateLayout.setVisibility(View.GONE);
        } else {
            Date date = new Date(ldate);
            Format format = new SimpleDateFormat("dd.mm.yyyy");
            time.setText(format.format(date));
        }
        findViewById(R.id.fabAddAimGoal).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivCancel:
                if(mChanges){
                    Log.wtf("aim", "here");
                    finishOkResult();
                }
                finish();
                break;
            case R.id.fabAddAimGoal:
                gotoAddEvent();
                break;
        }
    }

    private void gotoAddEvent(){
        Intent intent = new Intent(mContext, AddEventActivity.class);
        startActivityForResult(intent, 5);
    }

    private void deleteAim(){
        Toast.makeText(mContext, "Text", Toast.LENGTH_LONG).show();
        int id = mAim.getId();
        Log.wtf("id", String.valueOf(id));
        mGoal.deleteAimById(id);
        new UpdateGoalAsyncTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finishOkResult();
    }

    private void updateAim(){
        Intent intent = new Intent(mContext, EditAimActivity.class).putExtra(EditAimActivity.GOAL_OBJ, mGoal)
                .putExtra(EditAimActivity.AIM_OBJ_ID, mAim);
        startActivityForResult(intent, 4);
    }

    private void finishOkResult(){
        Intent returnIntent = new Intent().putExtra(GOAL_OBJ, mGoal);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 4) {
            if(resultCode == Activity.RESULT_OK){
                mChanges = true;
                Log.wtf("aim", "changes true");
                mAim = (Aim) data.getSerializableExtra(AIM_OBJ);
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ);
                new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mDetailAimAdapter.changeAllData(mGoal, mAim);
                initUI();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                ; // nothing
                Log.wtf("r", "canceled");
            }
        }
        /*
        if (requestCode == 2){
            if(resultCode == RESULT_OK){
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ);
                mDetailGoalAdapter.changeAllData(mGoal);
                initUI();
            }
            if (requestCode == RESULT_CANCELED){
                ;
                Log.wtf("r", "2 canceled");
            }
            if (resultCode == errorCode){
                ;
                Log.wtf("ERROR", "200");
            }
        }
        */
    }

    @Override
    public void onBackPressed() {
        Log.wtf("aim", "back press");
        if(mChanges){
            Log.wtf("aim", "here");
            Intent returnIntent = new Intent().putExtra(GOAL_OBJ, mGoal);
            setResult(Activity.RESULT_OK,returnIntent);
        }
        super.onBackPressed();
    }
}
