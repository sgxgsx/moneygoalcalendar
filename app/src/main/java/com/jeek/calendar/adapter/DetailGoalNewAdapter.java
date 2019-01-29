package com.jeek.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jeek.calendar.R;
import com.jeek.calendar.dialog.AddTaskDialog;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalList;

import java.util.List;

public class DetailGoalNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private Goal mGoal;
    private Aim mAim;
    private List<GoalList> lists;
    private int mWidth, margin_left, margin_top;

    public DetailGoalNewAdapter(Context context, Goal goal) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        lists = goal.getLists();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mWidth = (int) (displayMetrics.widthPixels * 0.75);
        margin_left = getInPx(20);
        margin_top = getInPx(10);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            final ListViewHolder viewHolder = (ListViewHolder) holder;
            viewHolder.mGoalList = lists.get(position);
            LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams( mWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin_left, margin_top, 0, 0);
            viewHolder.clAll.setLayoutParams(params);
            viewHolder.rvList.setLayoutManager(manager);
            viewHolder.mListAdapter = new ListAdapter(mContext, viewHolder.mGoalList);
            viewHolder.rvList.setAdapter(viewHolder.mListAdapter);
            viewHolder.llAddBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.addTask();
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
        Log.wtf("size", String.valueOf(lists.size()));
        return lists.size();
    }

    public void changeAllData(Goal goal) {
        mGoal = goal;
        lists = goal.getLists();
        notifyDataSetChanged();
    }

    public void changeList(GoalList goalList){
        ;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements AddTaskDialog.OnAddTaskListner {
        private ConstraintLayout clAll;
        private RecyclerView rvList;
        private LinearLayout llAddBar;
        private ListAdapter mListAdapter;
        private AddTaskDialog mAddTaskDialog;
        private GoalList mGoalList;
        private int position;

        public ListViewHolder(View itemView) {
            super(itemView);
            rvList = itemView.findViewById(R.id.rvList);
            llAddBar = itemView.findViewById(R.id.llAddBar);
            clAll = itemView.findViewById(R.id.clAll);
        }

        @Override
        public void onAddTask(GoalList goalList) {
            goalList.setId(getAdapterPosition());
            lists.set(goalList.getId(), goalList);
            mGoal.setLists(lists);
            new UpdateGoalAsyncTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            changeAllData(mGoal);
        }

        private void addTask() {
            if (mAddTaskDialog == null) {
                mAddTaskDialog = new AddTaskDialog(mContext, mGoalList,this);
            }
            mAddTaskDialog.show();
            //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        }
    }

}