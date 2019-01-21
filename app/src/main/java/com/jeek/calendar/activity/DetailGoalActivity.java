package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.DetailAimAdapter;
import com.jeek.calendar.adapter.DetailGoalAdapter;
import com.jeek.calendar.fragment.MembersFragment;
import com.jeek.calendar.task.goal.DeleteGoalTask;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailGoalActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, MembersFragment.OnFragmentInteractionListener, DrawerLayout.DrawerListener{
    public static final String GOAL_OBJ = "GOAL.Obj";
    public static final String GOAL_OBJW = "GOAL.Objw";
    public static final String GOAL_OBJ_AIM = "GOAL.Obj.Add.Aim";
    public static final String GOAL_OBJ_AIM_DETAIL = "GOAL.Obj.Detail.Aim";
    public static final String NOTE_OBJ = "Note.Obj";


    public static final int errorCode = 200;
    private static final int GO_BACK_CALL_BACK = 1;

    private DrawerLayout drawerLayout;
    private DoubleDrawerView doubleDrawerView;
    private NavigationView mainNavigationView, settingsNavigationView, secondNavigationView;
    private Fragment fragment;

    private Context mContext;
    private Goal mGoal;
    private RecyclerView rvDetail;
    private DetailGoalAdapter mDetailGoalAdapter;
    private TextView goalName, time, description;
    private ConstraintLayout dateLayout;
    private ImageView noteImage;
    private Toolbar mToolbar;
    private boolean buttonNotShowen = true;
    private View AddNote,AddAim, MenuButtonBackground;



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
        noteImage = findViewById(R.id.iNoteImage);
        goalName = findViewById(R.id.tvGoalName);
        time = findViewById(R.id.tvDeadlineGoal);
        dateLayout = findViewById(R.id.DateLayout);
        description = findViewById(R.id.tvNoteTextView);
        AddAim = findViewById(R.id.fabAddAimGoal);
        AddNote = findViewById(R.id.fabAddNoteGoal);
        MenuButtonBackground = findViewById(R.id.chooseMenuButtonBackground2);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        doubleDrawerView = (DoubleDrawerView) findViewById(R.id.double_drawer_view);
        mainNavigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        settingsNavigationView = (NavigationView) findViewById(R.id.settings_navigation_view);
        secondNavigationView   = findViewById(R.id.second_navigation_view);

        goalName.setText(mGoal.getGoal_name());
        description.setText(mGoal.getDescription());

        AddAim.setOnClickListener(this);
        AddNote.setOnClickListener(this);
        MenuButtonBackground.setOnClickListener(this);
        mainNavigationView.setNavigationItemSelectedListener(this);
        settingsNavigationView.setNavigationItemSelectedListener(this);
        secondNavigationView.setNavigationItemSelectedListener(this);
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

/*
    private BitmapDrawable setImage(String path){
        Bitmap selectedImage = BitmapFactory.decodeFile(path);
        return new BitmapDrawable(getResources(), selectedImage);
    }
*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llCancel:
                finish();
                break;
            case R.id.llGoMenu:
                openMenu();
                break;
            case R.id.fabAddAimGoal:
                if(buttonNotShowen){
                    showFloatingChoiceMenu();
                    Log.wtf("Not", "shown");
                } else{
                    gotoAddAimActivity();
                    Log.wtf("go", " to Add Event");
                }
                break;
            case R.id.fabAddNoteGoal:
                gotoNote();
                break;
            case R.id.chooseMenuButtonBackground2:
                hideFloatingChoiceMenu();
                break;
        }
    }


    private void showFloatingChoiceMenu() {
        MenuButtonBackground.setVisibility(View.VISIBLE);
        AddNote.setVisibility(View.VISIBLE);
        buttonNotShowen = false;
    }
    private void hideFloatingChoiceMenu() {
        Log.wtf("www", "hide");
        AddNote.setVisibility(View.INVISIBLE);
        MenuButtonBackground.setVisibility(View.INVISIBLE);
        buttonNotShowen = true;
    }

    private void gotoNote(){
        Intent intent = new Intent(mContext, NoteActivity.class).putExtra(NoteActivity.GOAL_OBJ, mGoal);
        startActivityForResult(intent, 3);
    }

    private void gotoAddAimActivity(){
        Intent intent = new Intent(mContext, AddAimActivity.class).putExtra(AddAimActivity.GOAL_OBJ, mGoal);
        startActivityForResult(intent, 2);
    }

    private void deleteGoal(){
        Toast.makeText(mContext, "Text", Toast.LENGTH_LONG).show();
        new DeleteGoalTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finish();
    }

    private void archiveGoal(){
        ;
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
        } else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ_AIM);
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
        } else if(requestCode == 3){
            if (resultCode == RESULT_OK){
                //mChanges = true;
                mGoal = (Goal) data.getSerializableExtra(GOAL_OBJ_AIM_DETAIL);
                new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mDetailGoalAdapter.changeAllData(mGoal);
                initUI();
            } else if (resultCode == RESULT_CANCELED){
                //nothhing
                Log.wtf("r", "canceled");
            }
        }
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
}
