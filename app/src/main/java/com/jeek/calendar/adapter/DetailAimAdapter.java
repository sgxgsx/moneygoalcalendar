package com.jeek.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.DetailAimActivity;
import com.jeek.calendar.activity.DetailEventActivity;
import com.jeek.calendar.activity.TaskActivity;
import com.jeek.calendar.utils.JeekUtils;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;
import com.jimmy.common.GoalDatabase.Task;
import com.jimmy.common.ItemWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailAimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private Goal mGoal;
    private Aim mAim;
    private List<ItemWrapper> items;

    public DetailAimAdapter(Context context, Goal goal, Aim aim) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        mAim = aim;
        items = aim.getItems();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return new ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false));
        } else if (viewType == 3) {
            Log.wtf("second", "note");
        }
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScheduleViewHolder) {
            final GoalSchedule schedule = (GoalSchedule) items.get(position);
            final ScheduleViewHolder viewHolder = (ScheduleViewHolder) holder;

            //viewHolder.vScheduleHintBlock.setBackgroundColor();
            viewHolder.tvScheduleTitle.setText(schedule.getTitle());
            if (schedule.getTime() != 0) {
                String time_to_display = JeekUtils.timeStamp2Time(schedule.getTime()) + " - " + JeekUtils.timeStamp2Time(schedule.getTime_end());
                viewHolder.tvScheduleTime.setText(time_to_display);
            } else {
                viewHolder.tvScheduleTime.setText("no time");
            }
            viewHolder.vScheduleHintBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("click", "click go to detail Schedule");
                    Intent intent = new Intent(mContext, DetailEventActivity.class).putExtra(DetailEventActivity.SCHEDULE_OBJ, schedule);
                    mActivity.startActivityForResult(intent, 3);
                }
            });
        } else if (holder instanceof TaskViewHolder) {
            Log.wtf("note", "note");
            Task note = (Task) items.get(position);
            final Task TaskFinal = new Task(note.getId(), note.getTitle(), note.getText(), note.getTime());
            final TaskViewHolder viewHolder = (TaskViewHolder) holder;
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(getInPx(8), getInPx(10), getInPx(8), getInPx(4));
            viewHolder.clTask.setLayoutParams(layoutParams);
            Log.wtf("create", "create");
            if (!note.getTitle().equals("")) {
                viewHolder.tvTitle.setVisibility(View.VISIBLE);
                String text = note.getTitle() + ":";
                viewHolder.tvTitle.setText(text);
            }

            Date currentTime = new Date(note.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d   HH:mm");  // T ODO CHANGE LOCALE локализировать
            String currentDateandTime = sdf.format(currentTime);
            viewHolder.tvTime.setText(currentDateandTime);
            viewHolder.tvText.setText(note.getText());
            viewHolder.clTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TaskActivity.class).putExtra(DetailAimActivity.GOAL_OBJ, mGoal).putExtra(DetailAimActivity.AIM_OBJ, mAim).putExtra(DetailAimActivity.NOTE_OBJ, TaskFinal);
                    mActivity.startActivityForResult(intent, 3);
                }
            });
        } else {
            Log.wtf("Bad", "news");
        }
    }

    private int getInPx(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mContext.getResources().getDisplayMetrics());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void changeAllData(Goal goal, Aim aim) {
        mGoal = goal;
        Log.wtf("wwsad", mGoal.getGoal_name());
        mAim = aim;
        items = aim.getItems();
        notifyDataSetChanged();
    }

    private class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout vScheduleHintBlock;
        private LinearLayout llScheduleState;
        private TextView tvScheduleTitle;
        private TextView tvScheduleTime;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            vScheduleHintBlock = (ConstraintLayout) itemView.findViewById(R.id.vScheduleHintBlock);
            llScheduleState = itemView.findViewById(R.id.llScheduleStateGoalScheduler);
            tvScheduleTitle = (TextView) itemView.findViewById(R.id.tvScheduleTitle);
            tvScheduleTime = (TextView) itemView.findViewById(R.id.tvScheduleTime);
        }
    }

    private class TaskViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout clTask;
        private TextView tvTitle, tvText, tvTime;

        public TaskViewHolder(View itemView) {
            super(itemView);
            clTask = itemView.findViewById(R.id.clTask);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTime = itemView.findViewById(R.id.tvTaskTime);
            tvText = itemView.findViewById(R.id.tvTaskText);
        }
    }

}
