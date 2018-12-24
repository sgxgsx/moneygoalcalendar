package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
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
import com.jeek.calendar.task.goal.DeleteGoalTask;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailGoalActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String GOAL_OBJ = "GOAL.Obj";
    public static final String GOAL_OBJW = "GOAL.Objw";
    public static final String GOAL_OBJ_AIM = "GOAL.Obj.Add.Aim";
    public static final String GOAL_OBJ_AIM_DETAIL = "GOAL.Obj.Detail.Aim";
    public static final String NOTE_OBJ = "Note.Obj";


    public static final int errorCode = 200;

    private Toolbar mToolbar;
    private Context mContext;
    private Goal mGoal;
    private RecyclerView rvDetail;
    private DetailGoalAdapter mDetailGoalAdapter;
    private TextView goalName, time, description;
    private ConstraintLayout dateLayout;
    private ImageView noteImage;
    //private ImageView detailImage;
    private boolean buttonNotShowen = true;
    private View AddNote,AddAim, MenuButtonBackground;
    // completed VLAD сделать DetailGoalActivity
    // completed VLAD удаление Goal
    // completed VLAD модифицирование Goal -> Создать GoalEditActivity
    // completed VLAD решить проблему прокрутки
    // completed VLAD добавление Goal -> Создать GoalAddActivity
    // completed VLAD поиграться с лейаутом (UI)
    // completed VLAD refactor GoalDatabase добавить общее, выполненое, невыполненое кол-во (3 int поля) ивентов в Goal, Aim
    // completed VLAD refactor GoalDatabase сделать в GoalSchedule время необязательным
    // completed VLAD сделать addAimActivity
    // completed VLAD сделать editAimActivity
    // completed VLAD сделать detailAimActivity
    // completed VLAD сделать deleteAim
    // TODO VLAD сделать addScheduleActivity            ( LEHA )
    // TODO VLAD сделать editScheduleActivity           ( LEHA )
    // TODO VLAD сделать deleteSchedule                 ( LEHA )



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
        findViewById(R.id.llCancel).setOnClickListener(this);
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
        description = findViewById(R.id.tvNoteTextView);
        AddAim = findViewById(R.id.fabAddAimGoal);
        AddNote = findViewById(R.id.fabAddNoteGoal);
        MenuButtonBackground = findViewById(R.id.chooseMenuButtonBackground2);
        //detailImage = findViewById(R.id.ivImageDetailGoal);

        noteImage = findViewById(R.id.iNoteImage);
        goalName.setText(mGoal.getGoal_name());
        description.setText(mGoal.getDescription());
        long ldate = mGoal.getDate_to();
        if(ldate == 0){
            dateLayout.setVisibility(View.GONE);
        } else {
            Date date = new Date(ldate);
            Format format = new SimpleDateFormat("dd.mm.yyyy");
            time.setText(format.format(date));
        }


        AddAim.setOnClickListener(this);
        AddNote.setOnClickListener(this);
        MenuButtonBackground.setOnClickListener(this);

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
}
