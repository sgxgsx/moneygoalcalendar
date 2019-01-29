package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jimmy.common.GoalDatabase.GoalList;
import com.jimmy.common.GoalDatabase.Note;

public class AddTaskDialog extends Dialog implements View.OnClickListener {
    private OnAddTaskListner onAddTaskListner;
    private GoalList mGoalList;
    private Context mContext;
    private EditText editText;
    private TextView textView;

    public AddTaskDialog(Context context, GoalList goalList, OnAddTaskListner listner) {
        super(context, R.style.DialogFullScreen);
        setContentView(R.layout.dialog_add_task);
        onAddTaskListner = listner;
        mGoalList = goalList;
        mContext = context;
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.llSave);
        textView.setOnClickListener(this);
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
        Note note = new Note(null,editText.getText().toString(), 2000000);
        mGoalList.addItem(note);
        Log.wtf("size", String.valueOf(mGoalList.getItems().size()));
        onAddTaskListner.onAddTask(mGoalList);
        //setResult(Activity.RESULT_OK, returnIntent);
    }

    public interface OnAddTaskListner {
        void onAddTask(GoalList goalList);
    }
}
