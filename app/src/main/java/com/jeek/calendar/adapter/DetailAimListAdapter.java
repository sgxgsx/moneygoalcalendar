package com.jeek.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalList;
import com.jimmy.common.ItemWrapper;

import java.util.ArrayList;
import java.util.List;


public class DetailAimListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private Goal mGoal;
    private Aim mAim;
    private List<GoalList> lists;
    private List<ItemWrapper> items;

    public DetailAimListAdapter(Context context, Goal goal, Aim aim) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        mAim = aim;
        items = aim.getItems();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetailAimListAdapter.ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            final ListViewHolder viewHolder = (ListViewHolder) holder;
            LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            viewHolder.rvList.setLayoutManager(manager);
            List<ItemWrapper> list = new ArrayList<>();
            for (int i = 0; i < 4; ++i) {
                list.add(items.get(i));
            }
            //viewHolder.mListAdapter = new ListAdapter(mContext, list);
            //viewHolder.rvList.setAdapter(viewHolder.mListAdapter);
        } else {
            Log.wtf("Bad", "news");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void changeAllData(Goal goal, Aim aim) {
        mGoal = goal;
        mAim = aim;
        items = aim.getItems();
        notifyDataSetChanged();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        //private ConstraintLayout clAll;
        private RecyclerView rvList;
        private LinearLayout llAddBar;
        private ListAdapter mListAdapter;

        public ListViewHolder(View itemView) {
            super(itemView);
            rvList = itemView.findViewById(R.id.rvList);
            llAddBar = itemView.findViewById(R.id.llAddBar);
        }
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout clNote;
        private TextView tvTitle, tvText, tvTime;

        public NoteViewHolder(View itemView) {
            super(itemView);
            clNote = (ConstraintLayout) itemView.findViewById(R.id.clNote);
            tvTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            tvTime = (TextView) itemView.findViewById(R.id.tvNoteTime);
            tvText = (TextView) itemView.findViewById(R.id.tvNoteText);
        }
    }
}
