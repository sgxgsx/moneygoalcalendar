package com.jeek.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.AddAimActivity;
import com.jeek.calendar.activity.DetailAimActivity;
import com.jeek.calendar.activity.DetailEventActivity;
import com.jeek.calendar.activity.EditGoalActivity;
import com.jeek.calendar.utils.JeekUtils;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailAimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private Activity mActivity;
    private Goal mGoal;
    private Aim mAim;
    private List<GoalSchedule> goalSchedules;


    public DetailAimAdapter(Context context, Goal goal, Aim aim) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        mAim = aim;
        goalSchedules = mAim.getScheduleList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new DetailAimAdapter.ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false));
        }
        Log.wtf("WWWWW", "БЛЯТЬ ТУТ ХУЕВО ПИЗДЕЦ");
        return new DetailAimAdapter.ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if(goalSchedules == null){
            return 0;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailGoalAdapter.ScheduleViewHolder) {
            Log.wtf("ID: Schedule", String.valueOf(position) + " and " + String.valueOf(goalSchedules.size()) + " position " + String.valueOf(position));
            final GoalSchedule schedule = goalSchedules.get(position);
            final DetailGoalAdapter.ScheduleViewHolder viewHolder = (DetailGoalAdapter.ScheduleViewHolder) holder;

            //viewHolder.vScheduleHintBlock.setBackgroundColor();
            viewHolder.tvScheduleTitle.setText(schedule.getTitle());
            if (schedule.getTime() != 0) {
                String time_to_display = JeekUtils.timeStamp2Time(schedule.getTime()) + " - " + JeekUtils.timeStamp2Time(schedule.getTime_end());
                viewHolder.tvScheduleTime.setText(time_to_display);
            } else {
                viewHolder.tvScheduleTime.setText("");
            }
            viewHolder.vScheduleHintBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("click", "click go to detail Schedule");
                    Intent intent = new Intent(mContext, DetailEventActivity.class).putExtra(DetailEventActivity.SCHEDULE_OBJ, schedule);
                    mActivity.startActivityForResult(intent, 3);
                }
            });
        } else{
            Log.wtf("Bad", "news");
        }
    }


    protected class ScheduleViewHolder extends RecyclerView.ViewHolder {
        protected ConstraintLayout vScheduleHintBlock;
        protected LinearLayout llScheduleState;
        protected TextView tvScheduleTitle;
        protected TextView tvScheduleTime;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            vScheduleHintBlock = (ConstraintLayout) itemView.findViewById(R.id.vScheduleHintBlock);
            llScheduleState = itemView.findViewById(R.id.llScheduleStateGoalScheduler);
            tvScheduleTitle = (TextView) itemView.findViewById(R.id.tvScheduleTitle);
            tvScheduleTime = (TextView) itemView.findViewById(R.id.tvScheduleTime);
        }
    }

    @Override
    public int getItemCount() {
        //Log.wtf("SIZE", String.valueOf(aims.size() + goalSchedules.size() + 2) + " or " + String.valueOf(aims.size() + goalSchedules.size()));
        if(goalSchedules == null){
            return 0;
        }
        return goalSchedules.size();
    }

    public void changeAllData(Goal goal, Aim aim) {
        mGoal = goal;
        Log.wtf("wwsad", mGoal.getGoal_name());
        mAim = aim;
        goalSchedules = mAim.getScheduleList();
        Log.wtf("wwsad", "here");
        notifyDataSetChanged();
    }

}
