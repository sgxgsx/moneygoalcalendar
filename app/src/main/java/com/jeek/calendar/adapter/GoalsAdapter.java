package com.jeek.calendar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.DetailGoalActivity;
import com.jeek.calendar.activity.EditGoalActivity;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder> {
    private Context mContext;
    private List<Goal> mGoals;


    public GoalsAdapter(Context context, List<Goal> goals) {
        mContext = context;
        mGoals = goals;
    }

    @Override
    public GoalsAdapter.GoalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GoalsAdapter.GoalsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goal, parent, false));
    }

    @Override
    public int getItemCount() {
        return mGoals.size();
    }

    @Override
    public void onBindViewHolder(GoalsAdapter.GoalsViewHolder holder, final int position) {
        final Goal goal = mGoals.get(position);

        holder.tvGoalName.setText(goal.getGoal_name());
        int count = 0;
        int done = 0;

        if(goal.getAims() != null){
            List<Aim> aims= goal.getAims();
            for(int i =0; i<aims.size(); ++i){
                List<GoalSchedule> goalSchedules = aims.get(i).getScheduleList();
                if(goalSchedules != null){
                    for(int j=0; j< goalSchedules.size(); ++j){
                        if(goalSchedules.get(j).isState()){
                            count += 1;
                        } else {
                            done += 1;
                        }
                    }
                }
            }
        }
        if(goal.getSchedules() != null){
            List<GoalSchedule> goalSchedules = goal.getSchedules();
            if(goalSchedules != null){
                for(int j=0; j< goalSchedules.size(); ++j){
                    if(goalSchedules.get(j).isState()){
                        count += 1;
                    } else {
                        done += 1;
                    }
                }
            }
        }

        holder.tvGoalPlannedProgress.setText(String.valueOf(count));
        holder.tvGoalDoneProgress.setText(String.valueOf(done));
        Date date = new Date(goal.getDate_to());
        Format format = new SimpleDateFormat("DD.MM.YYYY");
        holder.tvDateTo.setText(format.format(date));
        holder.clGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDetail(goal);
                Log.wtf("click", "click go to detail");
            }
        });

        holder.vChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoChange(goal);
                Log.wtf("goto", "Update");
            }
        });
        /*
        holder.sdvEventSet.close(false);
        String text = calendarClass.getCalendarName() + String.valueOf(calendarClass.isShow());
        holder.tvEventSetName.setText(text);

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTrueOrFalse(calendarClass);
                Log.wtf("click", "click")
                notifyDataSetChanged();
            }
        });
        */
    }

    private void gotoChange(Goal goal){
        mContext.startActivity(new Intent(mContext, EditGoalActivity.class).putExtra(EditGoalActivity.GOAL_OBJ, goal));
    }

    private void gotoDetail(Goal goal){
        mContext.startActivity(new Intent(mContext, DetailGoalActivity.class).putExtra(DetailGoalActivity.GOAL_OBJ, goal));
    }

    protected class GoalsViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout clGoal;
        private TextView tvGoalName;
        private TextView tvGoalPlannedProgress;
        private TextView tvGoalDoneProgress;
        private TextView tvDateTo;
        private View vChange;

        public GoalsViewHolder(View itemView) {
            super(itemView);
            clGoal = itemView.findViewById(R.id.clGoal);
            tvGoalName = itemView.findViewById(R.id.tvGoalName);
            tvGoalPlannedProgress = itemView.findViewById(R.id.tvGoalDoneEvents);
            tvGoalDoneProgress = itemView.findViewById(R.id.tvGoalAllEvents);
            tvDateTo = itemView.findViewById(R.id.tvGoalDate);
            vChange = itemView.findViewById(R.id.vChangeGoal);
        }
    }
    public void addGoal(Goal goal){

    }

    public void changeAllData(List<Goal> goals) {
        mGoals.clear();
        mGoals.addAll(goals);
        notifyDataSetChanged();
    }

    public void insertItem(Goal goal) {
        mGoals.add(goal);
        notifyItemInserted(mGoals.size() - 1);
    }

    public void removeItem(int position) {
        mGoals.remove(position);
        notifyDataSetChanged();
    }
}
