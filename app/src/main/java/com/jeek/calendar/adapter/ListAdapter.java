package com.jeek.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.TaskActivity;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalList;
import com.jimmy.common.GoalDatabase.Task;
import com.jimmy.common.ItemWrapper;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String GOAL_OBJ = "GOAL.Obj";
    public static final String GOALLIST_OBJ = "GOALLIST.Obj";
    private Goal mGoal;
    private GoalList mGoalList;
    private List<ItemWrapper> mItems;
    private Context mContext;


    public ListAdapter(Context context, GoalList goalList, Goal goal) {
        this.mItems = goalList.getItems();
        this.mContext = context;
        this.mGoalList = goalList;
        this.mGoal = goal;
        Log.wtf("sss", String.valueOf(goalList.getId()));
    }

    @NonNull

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_task, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if(viewHolder instanceof TaskViewHolder){
            final TaskViewHolder holder = (TaskViewHolder) viewHolder;
            Task note = (Task) mItems.get(position);
            holder.tvText.setText(note.getTitle());
            holder.clTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDetails(position);
                }
            });
        }
    }

    public void openDetails(int position){
        Intent intent = new Intent(mContext, TaskActivity.class);
        intent.putExtra(GOAL_OBJ, mGoal);
        intent.putExtra(GOALLIST_OBJ, mGoalList);
        intent.putExtra("int", position);
        ((Activity) mContext).startActivityForResult(intent, 4);
    }



    public void changeAllData(final List<ItemWrapper> items) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mItems.clear();
                mItems.addAll(items);
                notifyDataSetChanged();
            }
        }).start();
    }

    public void changeAllData(final int pos){
        notifyItemChanged(pos);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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
