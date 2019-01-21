package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.DetailAimAdapter;
import com.jeek.calendar.fragment.MembersFragment;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.tangke.slidemenu.SlideMenu;

public class DetailAimActivity extends AppCompatActivity implements View.OnClickListener/*, NavigationView.OnNavigationItemSelectedListener, MembersFragment.OnFragmentInteractionListener, DrawerLayout.DrawerListener*/{
    public static final String GOAL_OBJ = "GOAL.Obj.Detail.Aim";
    public static final String AIM_OBJ = "AIM.Obj.Detail.Aim";
    public static final String NOTE_OBJ = "Note.Obj";
    public static final int errorCode = 200;
    private static final int GO_BACK_CALL_BACK = 1;

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
    //////****private TextView aimName, time, description;
    //////****private ConstraintLayout dateLayout;
    private View AddNote,AddEvent, MenuButtonBackground;
    private boolean buttonNotShowen;
    SlideMenu slideMenu;
    //private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aim);




        mContext = getApplicationContext();
        rvDetail = findViewById(R.id.rvAimsEventsAimsDetailActivity);
        //linear   = findViewById(R.id.linear);
        //linear.setMinimumWidth(1000);

        findViewById(R.id.llCancel).setOnClickListener(this);
        //findViewById(R.id.llGoMenu).setOnClickListener(this);


        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            mAim = (Aim) getIntent().getSerializableExtra(AIM_OBJ);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvDetail.setLayoutManager(manager);
            DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setSupportsChangeAnimations(true);
            rvDetail.setItemAnimator(itemAnimator);
            mDetailAimAdapter = new DetailAimAdapter(this, mGoal, mAim);
            rvDetail.setAdapter(mDetailAimAdapter);
            initUI();
        }
    }

    private void initUI(){
        //////****aimName = findViewById(R.id.tvAimName);
        //////****time = findViewById(R.id.tvDeadlineAim);
        //////****dateLayout = findViewById(R.id.DateLayout);
        //////****description = findViewById(R.id.tvNoteTextView);
        /*
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        doubleDrawerView = (DoubleDrawerView) findViewById(R.id.double_drawer_view);
        mainNavigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        settingsNavigationView = (NavigationView) findViewById(R.id.settings_navigation_view);
        secondNavigationView   = findViewById(R.id.second_navigation_view);

        mainNavigationView.setNavigationItemSelectedListener(this);
        settingsNavigationView.setNavigationItemSelectedListener(this);
        secondNavigationView.setNavigationItemSelectedListener(this);

        drawerLayout.setDrawerListener(this);
        */

        AddNote = findViewById(R.id.fabAddNoteAim);
        AddEvent = findViewById(R.id.fabAddAimGoal);
        MenuButtonBackground = (View) findViewById(R.id.chooseMenuButtonBackground2);
        MenuButtonBackground.setVisibility(View.INVISIBLE);
        //////****aimName.setText(mAim.getName());
        //description.setText(mAim.getScheduleList().toString());
        //////****description.setText(mAim.getDescription());
        //long ldate = mAim.getDate_to();
        long ldate = 0;
        if(ldate == 0){
            //dateLayout.setVisibility(View.GONE);
            //time.setVisibility(View.GONE);
        } else {
            Date date = new Date(ldate);
            Format format = new SimpleDateFormat("dd.mm.yyyy");
            //////****time.setText(format.format(date));
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
            /*
            case R.id.llGoMenu:
                openMenu();
                break;
            */
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
        mGoal.deleteById(mAim.getId(), mAim.getViewType());
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
