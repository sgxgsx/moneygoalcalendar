package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class EditAimActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        SelectColorDialog.OnSelectColorListener {
    public static final String GOAL_OBJ = "GOAL.Obj.Edit.Aim";
    public static final String AIM_OBJ_ID = "GOAL.Obj.Edit.Aim.Id";
    private ConstraintLayout mToolBar;
    private Goal mGoal;
    private Aim mAim;
    private int id;
    private EditText title, description;
    private TextView time;
    private CheckBox cbtime;
    public String mColor;
    private SelectColorDialog mColorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aim);
        if (getIntent().hasExtra(GOAL_OBJ)) {
            mToolBar = findViewById(R.id.nbAddGoal);
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            mAim = (Aim) getIntent().getSerializableExtra(AIM_OBJ_ID);
            id = mAim.getId();
            mColor = mAim.getColor();
            mToolBar.setBackgroundColor(Color.parseColor(mColor));
            initUI();
        }


    }

    private void initUI(){
        title = findViewById(R.id.etNameGoal);
        description = findViewById(R.id.etDescription);
        time = findViewById(R.id.tvDeadlineGoal);
        cbtime = findViewById(R.id.cbTime);

        cbtime.setOnCheckedChangeListener(this);
        findViewById(R.id.tvSaveGoal).setOnClickListener(this);
        findViewById(R.id.tvDeadlineGoal).setOnClickListener(this);
        findViewById(R.id.ivChangeColor).setOnClickListener(this);
        findViewById(R.id.llCancel).setOnClickListener(this);
        description.setText(mAim.getDescription());
        title.setText(mAim.getName());
        time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSaveGoal:
                Log.wtf("ckick", "click click");
                changeAim();
                break;
            case R.id.tvDeadlineGoal:
                changeTime();
                break;
            case R.id.ivChangeColor:
                changeColor();
                break;
            case R.id.llCancel:
                cancel();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cbTime:
                checkTime();
                break;
        }
    }

    private void changeAim(){
        Log.wtf("ckick", "click click change");
        String aim_title = title.getText().toString();
        String aim_descr = description.getText().toString();
        mGoal.getItems().get(id).setName(aim_title); //setGoal_name(title.getText().toString());
        mGoal.getItems().get(id).setDescription(aim_descr);
        mGoal.getItems().get(id).setColor(mColor);
        mAim.setName(aim_title);
        mAim.setDescription(aim_descr);
        mAim.setColor(mColor);
        if(cbtime.isChecked()){
            //mGoal.setDate_to(0);
            Log.wtf("AIM", "set date");
        }
        new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Intent returnIntent = new Intent().putExtra(DetailAimActivity.AIM_OBJ, mAim).putExtra(DetailAimActivity.GOAL_OBJ, mGoal);
        setResult(Activity.RESULT_OK,returnIntent);
        Log.wtf("Sent", "sent");
        finish();
    }

    private void cancel(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void checkTime(){
        if(cbtime.isChecked()){
            time.setVisibility(View.GONE);
        } else{
            time.setVisibility(View.VISIBLE);
        }
    }

    private void changeTime(){
        if(!cbtime.isChecked()){
            Toast.makeText(getApplicationContext(), "unchecked", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Change time", Toast.LENGTH_LONG).show();
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

}