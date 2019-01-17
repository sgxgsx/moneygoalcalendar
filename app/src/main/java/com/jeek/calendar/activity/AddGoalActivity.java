package com.jeek.calendar.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.GoalsAdapter;
import com.jeek.calendar.task.goal.InsertGoalTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;
import com.jimmy.common.GoalDatabase.Note;

import java.util.ArrayList;
import java.util.List;

public class AddGoalActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "AddGoalActivity";
    private final int RESULT_LOAD_IMG = 100;
    private GoalsAdapter mGoalsAdapter;

    private EditText title, description;
    private ImageView ivGoal;
    private TextView time;
    private CheckBox cbtime;
    //private CheckBox aimsevents;
    private String image_path = "";

    // TODO LEHA добавь выбор времени в этот файл. (который будет вовзращать long)?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        ivGoal = findViewById(R.id.ivAddGoal);
        title = findViewById(R.id.etNameGoal);
        description = findViewById(R.id.etDescription);
        //aimsevents = findViewById(R.id.cbAimEvent);
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
        //boolean aimandevents = aimsevents.isChecked();
        long date_to = 0;
        if(!cbtime.isChecked()){
            date_to = 2040200100;
        }
        List<Aim> aimList = new ArrayList<>();
        List<GoalSchedule> goalSchedules = new ArrayList<>();
        Goal goal = new Goal(name, date_to, desc, image_path, aimList, goalSchedules, new ArrayList<Note>());
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

    private void setImage(String path){
        Bitmap selectedImage = BitmapFactory.decodeFile(path);
        BitmapDrawable background = new BitmapDrawable(getResources(), selectedImage);
        ivGoal.setImageDrawable(background);
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            image_path = getRealPathFromURI(getApplicationContext(), imageUri);
            setImage(image_path);
        }else {
            Log.wtf(TAG, "No image picked");
        }
    }
}
