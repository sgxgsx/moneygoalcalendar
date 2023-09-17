package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeek.calendar.R;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Goal;

public class EditGoalActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final String GOAL_OBJ = "GOAL.Obj";
    private static final String TAG = "EditGoalActivity";
    private final int RESULT_LOAD_IMG = 100;
    private Goal mGoal;
    private EditText title, description;
    private TextView time;
    private CheckBox cbtime;
    //private CheckBox aimsevents;
    private ImageView ivGoal;
    private String image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            initUI();
        }


    }

    private void initUI() {
        title = findViewById(R.id.etNameGoal);
        description = findViewById(R.id.etDescription);
        //aimsevents = findViewById(R.id.cbAimEvent);
        time = findViewById(R.id.tvDeadlineGoal);
        cbtime = findViewById(R.id.cbTime);
        ivGoal = findViewById(R.id.ivAddGoal);

        cbtime.setOnCheckedChangeListener(this);
        time.setOnClickListener(this);
        findViewById(R.id.llSaveGoal).setOnClickListener(this);
        findViewById(R.id.tvDeadlineGoal).setOnClickListener(this);
        findViewById(R.id.ivChangeColor).setOnClickListener(this);
        findViewById(R.id.llCancel).setOnClickListener(this);

        image_path = mGoal.getImage_path();

        // if not empty - set photo
        if (!mGoal.getImage_path().equals("")) {
            setImage(mGoal.getImage_path());
        }

        description.setText(mGoal.getDescription());
        title.setText(mGoal.getGoal_name());
        //aimsevents.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSaveGoal:
                changeGoal();
                break;
            case R.id.tvDeadlineGoal:
                changeTime();
                break;
            case R.id.ivChangeColor:
                changeImage();
                break;
            case R.id.llCancel:
                cancel();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cbTime:
                checkTime();
                break;
        }
    }

    private void changeGoal() {
        mGoal.setGoal_name(title.getText().toString());
        mGoal.setDescription(description.getText().toString());

        if (cbtime.isChecked()) mGoal.setDate_to(0);

        // if image_path differs from original one - change it
        if (!image_path.equals(mGoal.getImage_path())) mGoal.setImage_path(image_path);

        new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Intent returnIntent = new Intent().putExtra(GOAL_OBJ, mGoal);
        setResult(Activity.RESULT_OK, returnIntent);
        Log.wtf(TAG, mGoal.getGoal_name());
        Log.wtf(TAG, title.getText().toString());
        Log.wtf(TAG, "sent");
        finish();
    }

    private void cancel() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void checkTime() {
        if (cbtime.isChecked()) {
            time.setVisibility(View.GONE);
        } else {
            time.setVisibility(View.VISIBLE);
        }
    }

    private void changeTime() {
        if (!cbtime.isChecked()) {
            Toast.makeText(getApplicationContext(), "unchecked", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Change time", Toast.LENGTH_LONG).show();
    }

    private void changeImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    private void setImage(String path) {
        Bitmap selectedImage = BitmapFactory.decodeFile(path);
        BitmapDrawable background = new BitmapDrawable(getResources(), selectedImage);
        ivGoal.setImageDrawable(background);
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
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
        } else {
            Log.wtf(TAG, "No image picked");
        }
    }
}
