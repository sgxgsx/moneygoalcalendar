package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeek.calendar.R;
import com.jeek.calendar.dialog.SelectColorDialog;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;
import com.jimmy.common.GoalDatabase.Note;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddAimActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        SelectColorDialog.OnSelectColorListener{
    public static final String GOAL_OBJ = "GOAL.Obj.Add.Aim";
    public static final int errorCode = 200;
    public static final int GET_FROM_GALLERY = 3;
    private ConstraintLayout mToolBar;
    private Goal mGoal;
    private EditText title, description;
    private TextView time;
    private CheckBox cbtime;
    private String mColor = "#FFFFFF";
    private SelectColorDialog mColorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aim);
        mToolBar = findViewById(R.id.nbAddGoal);
        title = findViewById(R.id.etNameGoal);
        description = findViewById(R.id.etDescription);
        time = findViewById(R.id.tvDeadlineGoal);
        cbtime = findViewById(R.id.cbTime);

        cbtime.setOnCheckedChangeListener(this);
        findViewById(R.id.llCancel).setOnClickListener(this);
        findViewById(R.id.tvSaveGoal).setOnClickListener(this);
        findViewById(R.id.ivChangeColor).setOnClickListener(this);
        time.setOnClickListener(this);
        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
        } else{
            if(mGoal == null) throwError();
        }

    }


    // TODO LEHA добавь выбор времени в этот файл. (который будет вовзращать long)?



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llCancel:
                cancel();
                break;
            case R.id.tvSaveGoal:
                saveAim();
                break;
            case R.id.ivChangeColor:
                changeColor();
                break;
            case R.id.tvDeadlineGoal:
                changeTime();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id){
            case R.id.cbTime:
                showTime();
                break;
        }
    }

    private void saveAim(){
        String name = title.getText().toString();
        String desc = description.getText().toString();
        //TODO LEHA тут возвращаешь время. сюда. Мб просто Toast.
        long date_to = 0;
        if(!cbtime.isChecked()){
            date_to = 2040200150;
        }
        List<GoalSchedule> goalScheduleList = new ArrayList<>();
        Aim aim = new Aim(mGoal.getAims().size(), name, false, desc, mColor, goalScheduleList, new ArrayList<Note>());
        mGoal.addAim(aim);
        new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finishOkResult();
    }

    @Override
    public void onSelectColor(String color) {
        mToolBar.setBackgroundColor(Color.parseColor(color));
        mColor = color;
    }

    private void changeColor(){
        if (mColorDialog == null) {
            mColorDialog = new SelectColorDialog(this, this);
        }
        mColorDialog.show();
        //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }



    private void changeTime(){
        if(!cbtime.isChecked()){
            Toast.makeText(getApplicationContext(), "unchecked", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Change time", Toast.LENGTH_LONG).show();
    }

    private void showTime(){
        if(cbtime.isChecked()){
            time.setVisibility(View.VISIBLE);
        } else {
            time.setVisibility(View.INVISIBLE);
        }
    }

    private void cancel(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void throwError(){
        Intent returnIntent = new Intent();
        setResult(errorCode, returnIntent);
    }

    private void finishOkResult(){
        Intent returnIntent = new Intent().putExtra(GOAL_OBJ, mGoal);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Drawable drb = new BitmapDrawable(getResources(), bitmap);
                mToolBar.setBackground(drb);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
