package com.jeek.calendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.DetailAimAdapter;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.Note;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String GOAL_OBJ = "GOAL.Obj.Detail.Aim";
    public static final String AIM_OBJ = "AIM.Obj.Detail.Aim";
    static final String NOTE_OBJ = "Note";
    Toolbar mToolbar;
    EditText mTitle, mText;
    String Text, Title;
    boolean mDeleted, mIntoAim;
    Goal mGoal;
    Aim mAim;
    Note mNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mToolbar = findViewById(R.id.tbNoteActivity);
        findViewById(R.id.llCancel).setOnClickListener(this);
        findViewById(R.id.llDelete).setOnClickListener(this);
        mTitle = findViewById(R.id.etTitle);
        mText = findViewById(R.id.etText);
        Title = "";
        Text = "";
        mDeleted = false;
        if (getIntent().hasExtra(GOAL_OBJ)) {
            mGoal = (Goal) getIntent().getSerializableExtra(GOAL_OBJ);
            if (getIntent().hasExtra(AIM_OBJ)){
                mAim = (Aim) getIntent().getSerializableExtra(AIM_OBJ);
            }else mAim = null;
            if (getIntent().hasExtra(NOTE_OBJ)){
                mNote = (Note) getIntent().getSerializableExtra(NOTE_OBJ);
                mText.setText(mNote.getText());
                Text = mNote.getText();
                Title = mNote.getTitle();
                mTitle.setText(mNote.getTitle());
            } else mNote = null;

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llCancel:
                save();
                break;
            case R.id.llDelete:
                onDelete();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void save(){
        Log.wtf("SS", "-" + mTitle.getText().toString() + "- #" + mText.getText().toString() + "#-" + Text + "---" + Title + "--");
        //if mTitle.getText().toString().equals("")
        if (!mTitle.getText().toString().equals(Title) || !mText.getText().toString().equals(Text)){
            Log.wtf("h", "true");
        }
        if (!(mTitle.getText().toString().equals(Title) || mText.getText().toString().equals(Text))){

        }
        if (!mDeleted && (!mTitle.getText().toString().equals(Title) || !mText.getText().toString().equals(Text))){
            Log.wtf("here", "here");
            long time= Calendar.getInstance().getTimeInMillis();
            mNote = new Note(mTitle.getText().toString(), mText.getText().toString(), time);
            if (mAim != null){
                Log.wtf("aim", "ww");
                mNote.setId(mAim.getNoteList().size());
                mAim.addNote(mNote);
                mGoal.getAims().get(mAim.getId()).addNote(mNote);
            } else{
                mNote.setId(mGoal.getNoteList().size());
                mGoal.addNote(mNote);
            }

            Intent returnIntent = new Intent().putExtra(AIM_OBJ, mAim).putExtra(GOAL_OBJ, mGoal);
            setResult(Activity.RESULT_OK, returnIntent);
            Log.wtf("return", "norm");
            //finish();
            /*
            //TODO Note onPause
         */
        } else{
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            //finish();
        }
        finish();
    }

    public void onDelete(){
        mDeleted = true;
        if(mAim != null){
            mAim.deleteNote(mNote.getId());
            mGoal.getAims().get(mAim.getId()).deleteNote(mNote.getId());
        } else{
            mGoal.addNote(mNote);
        }
        new UpdateGoalAsyncTask(getApplicationContext(), mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        finish();
    }
    /*
    @Override
    public void onBackPressed() {
        Log.wtf("aim", "back press");
        save();
        super.onBackPressed();
    }
        */
}
