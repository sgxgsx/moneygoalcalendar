package com.jeek.calendar.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import com.jeek.calendar.adapter.GoalsAdapter;
import com.jeek.calendar.task.goal.InsertGoalTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;
import com.jimmy.common.GoalDatabase.Note;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddGoalActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private final int RESULT_LOAD_IMG = 100;
    private GoalsAdapter mGoalsAdapter;

    private ConstraintLayout ImagePicker;
    private EditText title, description;
    private TextView time;
    private CheckBox aimsevents, cbtime;

    // TODO LEHA добавь выбор времени в этот файл. (который будет вовзращать long)?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        ImagePicker = findViewById(R.id.nbAddGoal);
        title = findViewById(R.id.etNameGoal);
        description = findViewById(R.id.etDescription);
        aimsevents = findViewById(R.id.cbAimEvent);
        time = findViewById(R.id.tvDeadlineGoal);
        cbtime = findViewById(R.id.cbTime);

        cbtime.setOnCheckedChangeListener(this);
        findViewById(R.id.llCancel).setOnClickListener(this);
        findViewById(R.id.llSaveGoal).setOnClickListener(this);
        findViewById(R.id.ivChangeColor).setOnClickListener(this);
        time.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llCancel:
                finish();
                break;
            case R.id.llSaveGoal:
                saveGoal();
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

    private void saveGoal(){
        String name = title.getText().toString();
        String desc = description.getText().toString();
        //TODO LEHA тут возвращаешь время. сюда. Мб просто Toast.
        boolean aimandevents = aimsevents.isChecked();
        long date_to = 0;
        if(!cbtime.isChecked()){
            date_to = 2040200100;
        }
        List<Aim> aimList = new ArrayList<>();
        List<GoalSchedule> goalSchedules = new ArrayList<>();
        Goal goal = new Goal(name, date_to, desc, aimList, goalSchedules, new ArrayList<Note>());
        new InsertGoalTask(getApplicationContext(), goal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finish();
    }

    private void changeColor(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
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

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                BitmapDrawable background = new BitmapDrawable(getApplicationContext().getResources(), selectedImage);
                ImagePicker.setBackground(background);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.wtf("wtf", "Something went wrong AddGoalActivity");
            }

        }else {
            Log.wtf("wtf", "No image picked");
        }
    }
}
