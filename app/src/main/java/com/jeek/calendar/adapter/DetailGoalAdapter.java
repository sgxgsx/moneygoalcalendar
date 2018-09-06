package com.jeek.calendar.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.utils.JeekUtils;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;

import java.util.List;

public class DetailGoalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private Goal mGoal;
    private List<Aim> aims;
    private List<GoalSchedule> goalSchedules;


    public DetailGoalAdapter(Context context, Goal goal) {
        mContext = context;
        mGoal = goal;
        aims = mGoal.getAims();
        goalSchedules = mGoal.getSchedules();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new DetailGoalAdapter.AimsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_aim, parent, false));
        } else if (viewType == 2){
            return new DetailGoalAdapter.ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goal_scheduler, parent, false));
        } else if (viewType == 3){
            Log.wtf("www", "3");
        } else if (viewType == 4){
            Log.wtf("www", "4");
        } else if (viewType == 5){
            Log.wtf("www", "5");
        } else if (viewType == 6){
            Log.wtf("www", "6");
        } else if (viewType == 7){
            Log.wtf("www", "7");
        }

        return new DetailGoalAdapter.AimsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_aim, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if(aims == null){
            return 2;
        }
        if (position < aims.size()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailGoalAdapter.ScheduleViewHolder) {
            Log.wtf("ID: Schedule", String.valueOf(position) + " and " + String.valueOf(goalSchedules.size()) + " position " + String.valueOf(position - aims.size()));
            final GoalSchedule schedule = goalSchedules.get(position - aims.size() );
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
                }
            });
        } else if (holder instanceof  DetailGoalAdapter.AimsViewHolder){
            Log.wtf("ID: Aim", String.valueOf(position) + " and " + String.valueOf(aims.size()));
            final Aim aim = aims.get(position);
            final DetailGoalAdapter.AimsViewHolder viewHolder = (DetailGoalAdapter.AimsViewHolder) holder;
            viewHolder.tvGoalName.setText(aim.getName());
            viewHolder.tvGoalPlannedProgress.setText(String.valueOf(18));
            viewHolder.tvGoalDoneProgress.setText(String.valueOf(1));
            viewHolder.tvDateTo.setVisibility(View.GONE);
            viewHolder.clGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("click", "click go to detail");
                }
            });

            viewHolder.vChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("goto", "Update");
                }
            });
        }
    }


    protected class AimsViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout clGoal;
        private TextView tvGoalName;
        private TextView tvGoalPlannedProgress;
        private TextView tvGoalDoneProgress;
        private TextView tvDateTo;
        private View vChange;

        public AimsViewHolder(View itemView) {
            super(itemView);
            clGoal = itemView.findViewById(R.id.clGoal);
            tvGoalName = itemView.findViewById(R.id.tvGoalName);
            tvGoalPlannedProgress = itemView.findViewById(R.id.tvGoalDoneEvents);
            tvGoalDoneProgress = itemView.findViewById(R.id.tvGoalAllEvents);
            tvDateTo = itemView.findViewById(R.id.tvGoalDate);
            vChange = itemView.findViewById(R.id.vChangeGoal);
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
        if(aims == null && goalSchedules == null){
            return 0;
        }
        if(aims == null){
            return goalSchedules.size();
        }
        if(goalSchedules == null){
            return aims.size();
        }
        return (aims.size() + goalSchedules.size());
    }

    public void changeAllData(Goal goal) {
        mGoal = goal;
        Log.wtf("wwsad", mGoal.getGoal_name());
        aims = mGoal.getAims();
        goalSchedules = mGoal.getSchedules();
        Log.wtf("wwsad", "here");
        notifyDataSetChanged();
    }

}
