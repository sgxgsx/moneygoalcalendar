package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.DetailAimAdapter;
import com.jeek.calendar.adapter.DetailAimListAdapter;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;

import me.tangke.slidemenu.SlideMenu;

public class DetailAimActivity extends AppCompatActivity implements View.OnClickListener/*, NavigationView.OnNavigationItemSelectedListener, MembersFragment.OnFragmentInteractionListener, DrawerLayout.DrawerListener*/ {
    public static final String GOAL_OBJ = "GOAL.Obj.Detail.Aim";
    public static final String AIM_OBJ = "AIM.Obj.Detail.Aim";
    public static final String NOTE_OBJ = "Note.Obj";
    public static final int errorCode = 200;
    private static final int GO_BACK_CALL_BACK = 1;
    ImageView fab, fab1, fab2;
    SlideMenu slideMenu;
    private boolean isFABOpen = false;
    private View llBackground, llBackgroundBack;
    private DrawerLayout drawerLayout;
    private DoubleDrawerView doubleDrawerView;
    private NavigationView mainNavigationView, settingsNavigationView, secondNavigationView;
    private Fragment fragment;
    private boolean mChanges = false;
    private Toolbar mToolbar;
    private Context mContext;
    private Goal mGoal;
    private Aim mAim;
    private RecyclerView rvDetail;
    private DetailAimAdapter mDetailAimAdapter;
    private DetailAimListAdapter mDetailAimListAdapter;
    private View AddNote, AddEvent, MenuButtonBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aim);


        mContext = getApplicationContext();
        rvDetail = findViewById(R.id.rvAimsEventsAimsDetailActivity);

        findViewById(R.id.llCancel).setOnClickListener(this);


        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            mAim = (Aim) getIntent().getSerializableExtra(AIM_OBJ);
            if (mGoal.isMode()) {
                setListAdapter();
            } else {
                setUsualAdapter();
            }

            initUI();
        }
    }

    private void setListAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvDetail.setLayoutManager(manager);
        mDetailAimListAdapter = new DetailAimListAdapter(this, mGoal, mAim);
        rvDetail.setAdapter(mDetailAimListAdapter);
    }

    private void setUsualAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDetail.setLayoutManager(manager);
        mDetailAimAdapter = new DetailAimAdapter(this, mGoal, mAim);
        rvDetail.setAdapter(mDetailAimAdapter);
    }

    private void initUI() {
        llBackground = findViewById(R.id.chooseMenuButtonBackground2);
        llBackgroundBack = findViewById(R.id.BackGroundWhenChoice);
        fab = findViewById(R.id.FabMain);
        fab1 = findViewById(R.id.FabSub);
        fab2 = findViewById(R.id.FabMain_Sub);
        fab2.setVisibility(View.INVISIBLE);
        fab1.setVisibility(View.INVISIBLE);

        findViewById(R.id.chooseMenuButtonBackground2).setOnClickListener(this);
        findViewById(R.id.BackGroundWhenChoice).setOnClickListener(this);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("fab2.onClick", "triggered onClick");
                //add Goal
                gotoAddEvent();
                closeFABMenu();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("fab1.onClick", "triggered onClick");
                //add MoneyGoal
                gotoNote();
                closeFABMenu();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("ss", "wwww");
                if (!isFABOpen) {
                    Log.wtf("fab.onClick", "triggered onClick");
                    showFABMenu();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.wtf("c", "click");
        switch (v.getId()) {
            case R.id.llCancel:
                if (mChanges) {
                    Log.wtf("aim", "here");
                    finishOkResult();
                }
                finish();
                break;
        }
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

    private void gotoNote() {
        Intent intent = new Intent(mContext, NoteActivity.class).putExtra(GOAL_OBJ, mGoal)
                .putExtra(AIM_OBJ, mAim);
        startActivityForResult(intent, 6);
    }

    private void gotoAddEvent() {
        Intent intent = new Intent(mContext, AddEventActivity.class);
        startActivityForResult(intent, 5);
    }

    private void deleteAim() {
        //mGoal.deleteById(mAim.getId(), mAim.getViewType());
        new UpdateGoalAsyncTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finishOkResult();
    }

    private void updateAim() {
        Intent intent = new Intent(mContext, EditAimActivity.class).putExtra(EditAimActivity.GOAL_OBJ, mGoal)
                .putExtra(EditAimActivity.AIM_OBJ_ID, mAim);
        startActivityForResult(intent, 4);
    }

    private void finishOkResult() {
        Intent returnIntent = new Intent().putExtra(GOAL_OBJ, mGoal);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void changeAllData() {
        if (mGoal.isMode()) mDetailAimListAdapter.changeAllData(mGoal, mAim);
        else mDetailAimAdapter.changeAllData(mGoal, mAim);
    }

    private void updateGoalAsyncTask() {
        new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                mChanges = true;
                mAim = (Aim) data.getSerializableExtra(AIM_OBJ);
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ);
                updateGoalAsyncTask();
                changeAllData();
                initUI();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.wtf("r", "canceled");
            }
        }
        if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                mChanges = true;
                mAim = (Aim) data.getSerializableExtra(AIM_OBJ);
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ);
                updateGoalAsyncTask();
                changeAllData();
                initUI();
            } else if (resultCode == RESULT_CANCELED) {
                Log.wtf("r", "canceled");
            }
        }
    }
    /*
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

    public void openMenu(){
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
    public void onFragmentInteraction(int callback) {
        Log.wtf("callback", "callback");
        if(callback == GO_BACK_CALL_BACK){
            doubleDrawerView.closeThirdDrawer();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.wtf("menu", "onNavigationItem");
        switch (item.getItemId()) {
            case R.id.menu_follow:
                doubleDrawerView.openInnerDrawer();
                break;

            case R.id.menu_close_settings:
                doubleDrawerView.closeInnerDrawer();
                break;

            case R.id.menu_screen_3:
                Log.wtf("menu", "open");
                doubleDrawerView.openSecondDrawer();
                break;

            case R.id.menu_screen_1:
                doubleDrawerView.openThirdDrawer();
                break;

            case R.id.fab_add:
                doubleDrawerView.closeSecondDrawer();
                break;

            // Additional cases as needed
            // This example simply Toasts the title for the extra sample items

            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    */
}
