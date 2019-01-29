package com.jeek.calendar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jimmy.common.GoalDatabase.GoalList;
import com.jimmy.common.GoalDatabase.Note;
import com.jimmy.common.ItemWrapper;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemWrapper> mItems;
    private Context mContext;


    public ListAdapter(Context context, GoalList goalList) {
        this.mItems = goalList.getItems();
        this.mContext = context;
    }

    @NonNull

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NoteViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_task, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof NoteViewHolder){
            final NoteViewHolder holder = (NoteViewHolder) viewHolder;
            Note note = (Note) mItems.get(position);
            holder.tvText.setText(note.getText());
        }
    }

    public void changeAllData(List<ItemWrapper> items) {
        mItems.clear();
        Log.wtf("in adapter", String.valueOf(items.size()));
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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
