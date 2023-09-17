package com.jeek.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jeek.calendar.R;
import com.jeek.calendar.dialog.AddTaskDialog;
import com.jeek.calendar.task.goal.UpdateGoalAsyncTask;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalList;

import java.util.List;

public class DetailGoalNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private Goal mGoal;
    private List<GoalList> lists;
    //private List<ListAdapter> mAdapters;

    private LinearLayout.LayoutParams mParams;
    private int mWidth, margin_left, margin_top, set_70_percent_width;

    public DetailGoalNewAdapter(Context context, Goal goal) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        lists = goal.getLists();
        //mAdapters = new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        set_70_percent_width = (int) (displayMetrics.widthPixels * 0.7);
        margin_left = getInPx(20);
        margin_top = getInPx(10);
        mParams = new LinearLayout.LayoutParams( set_70_percent_width, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParams.setMargins(margin_left, margin_top, 0, margin_top);
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
            viewHolder.mTitle.setText(viewHolder.mGoalList.getName());
            viewHolder.rvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            viewHolder.clAll.setLayoutParams(mParams);
            viewHolder.mListAdapter = new ListAdapter(mContext, viewHolder.mGoalList, mGoal);
            viewHolder.mListAdapter.setHasStableIds(true);
            viewHolder.rvList.setItemViewCacheSize(20);
            viewHolder.rvList.setDrawingCacheEnabled(true);
            viewHolder.rvList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            viewHolder.rvList.setAdapter(viewHolder.mListAdapter);
            viewHolder.llAddBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.addTask();
                }
            });
            //mAdapters.add(viewHolder.mListAdapter);
        }
    }

    private int getInPx(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mContext.getResources().getDisplayMetrics());
    }

    @Override
    public long getItemId(int position) {
        GoalList gl = lists.get(position);
        return gl.getId();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void changeAllData(int position) {
        notifyItemChanged(position);
    }

    public void changeAllData(int position, int pos, Goal goal){
        // change data in ListAdapter
        mGoal = goal;
        lists = mGoal.getLists();
        //mAdapters.get(position).changeAllData(pos);
        changeAllData(position);
    }

    public void changeAllData(Goal goal) {
        mGoal = goal;
        lists = goal.getLists();
        notifyDataSetChanged();
    }



    private class ListViewHolder extends RecyclerView.ViewHolder implements AddTaskDialog.OnAddTaskListner {
        private LinearLayout clAll;
        private RecyclerView rvList;
        private LinearLayout llAddBar;
        private ListAdapter mListAdapter;
        private GoalList mGoalList;
        private EditText mTitle;

        public ListViewHolder(View itemView) {
            super(itemView);
            rvList = itemView.findViewById(R.id.rvList);
            llAddBar = itemView.findViewById(R.id.llAddBar);
            clAll = itemView.findViewById(R.id.clAll);
            mTitle = itemView.findViewById(R.id.etToolBarText);
        }

        @Override
        public void onAddTask(GoalList goalList) {
            new AddTaskAsyncTask(mContext, goalList).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        private void addTask() {
            AddTaskDialog dialog = new AddTaskDialog(mContext, mGoalList,this);
            dialog.getWindow().setLayout(set_70_percent_width, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();

        }


        private class AddTaskAsyncTask extends AsyncTask<Void, Void, Void> {

            protected Context mContext;
            private GoalList goalList;

            private AddTaskAsyncTask(Context context, GoalList goalList) {
                mContext = context;
                this.goalList = goalList;
            }

            @Override
            protected Void doInBackground(Void...voids) {
                goalList.setId(getAdapterPosition());
                lists.set(goalList.getId(), goalList);
                mGoal.setLists(lists);
                new UpdateGoalAsyncTask(mContext, mGoal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                changeAllData(goalList.getId());
            }
        }
    }

}