package com.jeek.calendar.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jeek.calendar.R;
import com.jeek.calendar.activity.DetailGoalActivity;
import com.jimmy.common.GoalDatabase.Goal;

import java.io.File;
import java.util.List;

public class  GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder> {
    private Context mContext;
    private Resources mResources;
    private List<Goal> mGoals;
    final String TAG = "GoalsAdapter";


    public GoalsAdapter(Context context, List<Goal> goals) {
        mContext = context;
        mGoals = goals;
        mResources = context.getResources();
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
        if(!goal.getImage_path().equals("")){
            Glide.with(mContext).load(new File(goal.getImage_path())).into(holder.imageGoal);
        }
        holder.tvGoalName.setText(goal.getGoal_name());
        holder.tvGoalPlannedProgress.setText(String.valueOf(goal.getInprogress()));
        holder.tvGoalDoneProgress.setText(String.valueOf(goal.getDoneschedules()));
        holder.clGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDetail(goal);
                Log.wtf(TAG, "click go to detail");
            }
        });

    }


    private void gotoDetail(Goal goal){
        mContext.startActivity(new Intent(mContext, DetailGoalActivity.class).putExtra(DetailGoalActivity.GOAL_OBJ, goal));
    }

    protected class GoalsViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout clGoal;
        private TextView tvGoalName;
        private TextView tvGoalPlannedProgress;
        private TextView tvGoalDoneProgress;
        private ImageView imageGoal;

        public GoalsViewHolder(View itemView) {
            super(itemView);
            clGoal = itemView.findViewById(R.id.clGoal);
            tvGoalName = itemView.findViewById(R.id.tvGoalName);
            tvGoalPlannedProgress = itemView.findViewById(R.id.tvGoalAllEvents);
            tvGoalDoneProgress = itemView.findViewById(R.id.tvGoalDoneEvents);
            imageGoal = itemView.findViewById(R.id.ivImageGoal);
        }
    }

    public void changeAllData(List<Goal> goals) {
        mGoals.clear();

        mGoals.addAll(goals);
        notifyDataSetChanged();
    }
}
