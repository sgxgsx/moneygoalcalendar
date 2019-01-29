package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.DetailGoalNewAdapter;
import com.jeek.calendar.fragment.OnFragmentInteractionListener;
import com.jeek.calendar.fragment.OnGoalSettingsFragmentListner;
import com.jeek.calendar.task.goal.DeleteGoalTask;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalList;
import com.jimmy.common.ItemWrapper;

import java.util.ArrayList;

public class DetailGoalActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, OnGoalSettingsFragmentListner, DrawerLayout.DrawerListener {
    public static final String GOAL_OBJ = "GOAL.Obj";
    public static final String GOAL_OBJW = "GOAL.Objw";
    public static final String GOAL_OBJ_AIM = "GOAL.Obj.Add.Aim";
    public static final String GOAL_OBJ_AIM_DETAIL = "GOAL.Obj.Detail.Aim";
    public static final String NOTE_OBJ = "Note.Obj";

    public static final int errorCode = 200;
    private static final int GO_BACK_CALL_BACK = 1;
    private static final int CHECKED_CALL_BACK = 2;
    private static final int UNCHECKED_CALL_BACK = 3;

    private DrawerLayout drawerLayout;
    private DoubleDrawerView doubleDrawerView;
    private NavigationView mainNavigationView;
    private Fragment fragment;

    private boolean isFABOpen = false;
    private ImageView fab, fab1, fab2;
    private View llBackground, llBackgroundBack;

    private Context mContext;
    private Goal mGoal;
    private RecyclerView rvDetail;
    private DetailGoalNewAdapter mDetailGoalAdapter;
    //private TextView goalName, time, description;
    private ConstraintLayout dateLayout;
    private ImageView noteImage;
    private Toolbar mToolbar;
    private boolean buttonNotShowen = true;
    private View AddNote, AddAim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_goal);
        mContext = getApplicationContext();
        rvDetail = findViewById(R.id.rvAimsEventsGoalDetailActivity);

        findViewById(R.id.llCancel).setOnClickListener(this);
        findViewById(R.id.llGoMenu).setOnClickListener(this);

        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvDetail.setLayoutManager(manager);
            mDetailGoalAdapter = new DetailGoalNewAdapter(this, mGoal);
            rvDetail.setAdapter(mDetailGoalAdapter);
            initUI();
            initFab();
        }


    }


    private void initUI() {
        //noteImage = findViewById(R.id.iNoteImage);
        //goalName = findViewById(R.id.tvGoalName);
        //time = findViewById(R.id.tvDeadlineGoal);
        //dateLayout = findViewById(R.id.DateLayout);
        //description = findViewById(R.id.tvNoteTextView);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        doubleDrawerView = (DoubleDrawerView) findViewById(R.id.double_drawer_view);
        mainNavigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        //goalName.setText(mGoal.getGoal_name());
        //description.setText(mGoal.getDescription());

        mainNavigationView.setNavigationItemSelectedListener(this);
        drawerLayout.setDrawerListener(this);
        //TODO на потом : нужно при создании и эдите Гоала добавить поле с чекбоксом "Показывать ли картинку в бэкграунде" а здесь делать проверку - нужно ли показывать
        //TODO на потом : если да, то переделать item_aim!
        /*
        if(!mGoal.getImage_path().equals("")){
            BitmapDrawable background = setImage(mGoal.getImage_path());
            detailImage.setImageDrawable(background);
        }
        */


    }

    private void initFab() {
        llBackground = findViewById(R.id.chooseMenuButtonBackground2);
        llBackgroundBack = findViewById(R.id.BackGroundWhenChoice);
        findViewById(R.id.chooseMenuButtonBackground2).setOnClickListener(this);
        findViewById(R.id.BackGroundWhenChoice).setOnClickListener(this);
        fab = findViewById(R.id.FabMain);
        fab1 = findViewById(R.id.FabSub);
        fab2 = findViewById(R.id.FabMain_Sub);
        fab2.setVisibility(View.INVISIBLE);
        fab1.setVisibility(View.INVISIBLE);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("fab2.onClick", "triggered onClick");
                //do smth
                gotoNote();
                closeFABMenu();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("fab1.onClick", "triggered onClick");
                //do smth
                gotoAddAimActivity();
                closeFABMenu();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    Log.wtf("fab.onClick", "triggered onClick");
                    showFABMenu();
                }
            }
        });
    }

    private void showFABMenu() {
        isFABOpen = true;
        fab1.setVisibility(View.VISIBLE);
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab1.animate().alpha(255);
        fab2.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
        llBackground.setVisibility(View.VISIBLE);
        llBackgroundBack.setVisibility(View.VISIBLE);
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().alpha(0);
        fab1.animate().translationY(0);
        fab2.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE);
        llBackground.setVisibility(View.INVISIBLE);
        llBackgroundBack.setVisibility(View.INVISIBLE);

    }
/*
    private BitmapDrawable setImage(String path){
        Bitmap selectedImage = BitmapFactory.decodeFile(path);
        return new BitmapDrawable(getResources(), selectedImage);
    }
*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCancel:
                finish();
                break;
            case R.id.llGoMenu:
                openMenu();
                break;
            case R.id.chooseMenuButtonBackground2:
                closeFABMenu();
                break;
        }
    }


    private void gotoNote() {
        //Intent intent = new Intent(mContext, NoteActivity.class).putExtra(NoteActivity.GOAL_OBJ, mGoal);
        //startActivityForResult(intent, 3);
        GoalList goalList = new GoalList(mGoal.getLists().size(), "name", new ArrayList<ItemWrapper>());
        mGoal.addList(goalList);
        updateGoalAsyncTask();
        mDetailGoalAdapter.changeAllData(mGoal);
        initUI();
    }

    private void gotoAddAimActivity() {
        Intent intent = new Intent(mContext, AddAimActivity.class).putExtra(AddAimActivity.GOAL_OBJ, mGoal);
        startActivityForResult(intent, 2);
    }

    private void deleteGoal() {
        Toast.makeText(mContext, "Text", Toast.LENGTH_LONG).show();
        new DeleteGoalTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finish();
    }

    private void archiveGoal() {
        ;
    }

    private void updateGoal() {
        Intent intent = new Intent(mContext, EditGoalActivity.class).putExtra(DetailGoalActivity.GOAL_OBJ, mGoal);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJW);
                mDetailGoalAdapter.changeAllData(mGoal);
                initUI();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                ; // nothing
                Log.wtf("r", "canceled");
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ_AIM);
                mDetailGoalAdapter.changeAllData(mGoal);
                initUI();
            }
            if (requestCode == RESULT_CANCELED) {
                ;
                Log.wtf("r", "2 canceled");
            }
            if (resultCode == errorCode) {
                ;
                Log.wtf("ERROR", "200");
            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                //mChanges = true;
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ_AIM_DETAIL);
                updateGoalAsyncTask();
                mDetailGoalAdapter.changeAllData(mGoal);
                initUI();
            } else if (resultCode == RESULT_CANCELED) {
                //nothhing
                Log.wtf("r", "canceled");
            }
        }
    }

    private void updateGoalAsyncTask() {
        new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void openMenu() {
        drawerLayout.openDrawer(Gravity.END);
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        Log.wtf("closed", "closed");
        doubleDrawerView.setDisplayedChild(0);
    }

    @Override
    public void onDrawerStateChanged(int i) {
        Log.wtf("state", String.valueOf(i));
    }


    @Override
    public void onGoalSettingsFragmentListner(int callback) {
        switch (callback) {
            case GO_BACK_CALL_BACK:
                doubleDrawerView.closeFragment();
                break;
            case CHECKED_CALL_BACK:
                Log.wtf("check", "check in detail_goal_activity");
                mGoal.setMode(true);
                updateGoalAsyncTask();
                break;
            case UNCHECKED_CALL_BACK:
                Log.wtf("check", "uncheck in detail_goal_activity");
                mGoal.setMode(false);
                updateGoalAsyncTask();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(int callback) {
        Log.wtf("callback", "callback");
        if (callback == GO_BACK_CALL_BACK) {
            doubleDrawerView.closeFragment();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.wtf("menu", "onNavigationItem");
        switch (item.getItemId()) {
            case R.id.menu_members:
                doubleDrawerView.openMembers();
                break;
            case R.id.menu_activity:
                doubleDrawerView.openActivity();
                break;
            case R.id.menu_addons:
                doubleDrawerView.openAddons();
                break;
            case R.id.menu_archived_tasks:
                doubleDrawerView.openArchivedTasks();
                break;
            case R.id.menu_archived_lists:
                doubleDrawerView.openArchivedLists();
                break;
            case R.id.menu_goal_settings:
                doubleDrawerView.openGoalSettings();
                break;

            case R.id.menu_watch:
                Log.wtf("menu", "watch");
                break;

            case R.id.menu_copy:
                Log.wtf("menu", "copy");
                break;

            case R.id.menu_share:
                Log.wtf("menu", "watch");
                break;
        }
        return true;
    }
}
