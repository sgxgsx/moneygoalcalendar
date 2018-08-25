package com.jeek.calendar.adapter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.DetailEventActivity;
import com.jeek.calendar.activity.DetailGoalActivity;
import com.jeek.calendar.widget.SlideDeleteView;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;

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
        String plannedProgress = "Planned: " + String.valueOf(2) + " Events (Improve)";
        String doneProgress = "Done: 0 Events (Improve)";
        holder.tvGoalPlannedProgress.setText(plannedProgress);
        holder.tvGoalDoneProgress.setText(doneProgress);
        Date date = new Date(goal.getDate_to());
        Format format = new SimpleDateFormat("EEEE, MMMM d, HH:mm");
        holder.tvDateTo.setText(format.format(date));
        holder.tvMoney.setVisibility(View.GONE);
        holder.clGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDetail(goal);
                Log.wtf("click", "click go to detail");
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
                Log.wtf("click", "click");
                notifyDataSetChanged();
            }
        });
        */
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
        private TextView tvMoney;

        public GoalsViewHolder(View itemView) {
            super(itemView);
            clGoal = itemView.findViewById(R.id.clGoal);
            tvGoalName = itemView.findViewById(R.id.tvGoalName);
            tvGoalPlannedProgress = itemView.findViewById(R.id.tvGoalPlannedProgress);
            tvGoalDoneProgress = itemView.findViewById(R.id.tvGoalDoneProgress);
            tvDateTo = itemView.findViewById(R.id.tvGoalDate);
            tvMoney = itemView.findViewById(R.id.tvGoalMoney);
        }
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
