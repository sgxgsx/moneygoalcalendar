package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jimmy.common.GoalDatabase.GoalList;
import com.jimmy.common.GoalDatabase.Task;

public class AddTaskDialog extends Dialog implements View.OnClickListener {
    private OnAddTaskListner onAddTaskListner;
    private GoalList mGoalList;
    private Context mContext;
    private EditText etDescription, etTitle;
    private TextView textView;

    public AddTaskDialog(Context context, GoalList goalList, OnAddTaskListner listner) {
        super(context, R.style.DialogFullScreen);
        setContentView(R.layout.dialog_add_task);
        onAddTaskListner = listner;
        mGoalList = goalList;
        mContext = context;
        etDescription = findViewById(R.id.etDescription);
        etTitle       = findViewById(R.id.etTitle);

        textView = findViewById(R.id.llSave);
        textView.setOnClickListener(this);
        if(etTitle.requestFocus()) {
            try{
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            } catch (NullPointerException s){
                Log.wtf("Null pointer:", "print");
                s.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSave:
                save();
                dismiss();
                break;
        }
    }

    private void save() {
        Task task = new Task(etTitle.getText().toString(),etDescription.getText().toString(), 2000000);
        mGoalList.addItem(task);
        Log.wtf("size", String.valueOf(mGoalList.getItems().size()));
        onAddTaskListner.onAddTask(mGoalList);
    }

    public interface OnAddTaskListner {
        void onAddTask(GoalList goalList);
    }
}
