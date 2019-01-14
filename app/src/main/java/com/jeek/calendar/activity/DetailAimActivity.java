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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.DetailAimAdapter;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.tangke.slidemenu.SlideMenu;

public class DetailAimActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String GOAL_OBJ = "GOAL.Obj.Detail.Aim";
    public static final String AIM_OBJ = "AIM.Obj.Detail.Aim";
    public static final String NOTE_OBJ = "Note.Obj";
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
    private View AddNote,AddEvent, MenuButtonBackground;
    private boolean buttonNotShowen;
    SlideMenu slideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSlideMenu();
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
        findViewById(R.id.llCancel).setOnClickListener(this);
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
    private void setSlideMenu(){
        slideMenu = new SlideMenu(this);
        slideMenu.setEdgeSlideEnable(true);
        setContentView(slideMenu);
        LayoutInflater content = getLayoutInflater();
        View contentView = content.inflate(R.layout.activity_detail_goal, null);
        slideMenu.addView(contentView, new SlideMenu.LayoutParams(
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_CONTENT));


        LayoutInflater contentMenu = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentMenuView = contentMenu.inflate(R.layout.detail_aim_right_slide_menu, null);

        slideMenu.addView(contentMenuView, new SlideMenu.LayoutParams(800,
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_SECONDARY_MENU));
    }

    private void initUI(){
        aimName = findViewById(R.id.tvAimName);
        time = findViewById(R.id.tvDeadlineAim);
        dateLayout = findViewById(R.id.DateLayout);
        description = findViewById(R.id.tvNoteTextView);

        AddNote = findViewById(R.id.fabAddNoteAim);
        AddEvent = findViewById(R.id.fabAddAimGoal);
        MenuButtonBackground = (View) findViewById(R.id.chooseMenuButtonBackground2);
        MenuButtonBackground.setVisibility(View.INVISIBLE);
        aimName.setText(mAim.getName());
        //description.setText(mAim.getScheduleList().toString());
        description.setText(mAim.getDescription());
        //long ldate = mAim.getDate_to();
        long ldate = 0;
        if(ldate == 0){
            //dateLayout.setVisibility(View.GONE);
            //time.setVisibility(View.GONE);
        } else {
            Date date = new Date(ldate);
            Format format = new SimpleDateFormat("dd.mm.yyyy");
            time.setText(format.format(date));
        }
        buttonNotShowen = true;
        findViewById(R.id.fabAddAimGoal).setOnClickListener(this);
        findViewById(R.id.fabAddNoteAim).setOnClickListener(this);
        findViewById(R.id.chooseMenuButtonBackground2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.wtf("c", "click");
        switch (v.getId()){
            case R.id.llCancel:
                if(mChanges){
                    Log.wtf("aim", "here");
                    finishOkResult();
                }
                finish();
                break;
            case R.id.fabAddAimGoal:
                if(buttonNotShowen){
                    showFloatingChoiceMenu();
                    Log.wtf("Not", "shown");
                } else{
                    gotoAddEvent();
                    Log.wtf("go", " to Add Event");
                }
                break;
            case R.id.fabAddNoteAim:
                gotoNote();
                Log.wtf("go", "to add note");
                break;
            case R.id.chooseMenuButtonBackground2:
                hideFloatingChoiceMenu();
                break;//TODO исправить то что оно не вызвается.
        }
    }

    private void showFloatingChoiceMenu() {
        AddNote.setVisibility(View.VISIBLE);
        findViewById(R.id.chooseMenuButtonBackground2).setVisibility(View.VISIBLE);
        findViewById(R.id.chooseMenuButtonBackground2).setOnClickListener(this);
        buttonNotShowen = false;
    }
    private void hideFloatingChoiceMenu() {
        Log.wtf("www", "hide");
        AddNote.setVisibility(View.INVISIBLE);
        MenuButtonBackground.setVisibility(View.INVISIBLE);
        findViewById(R.id.chooseMenuButtonBackground2).setVisibility(View.INVISIBLE);
        buttonNotShowen = true;
    }

    private void gotoNote(){
        Intent intent = new Intent(mContext, NoteActivity.class).putExtra(GOAL_OBJ, mGoal)
                .putExtra(AIM_OBJ, mAim);
        startActivityForResult(intent, 6);
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
        if (requestCode == 6){
            if (resultCode == RESULT_OK){
                mChanges = true;
                mAim = (Aim) data.getSerializableExtra(AIM_OBJ);
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ);
                new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mDetailAimAdapter.changeAllData(mGoal, mAim);
                initUI();
            } else if (resultCode == RESULT_CANCELED){
                //nothhing
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
