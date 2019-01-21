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
import com.jeek.calendar.activity.NoteActivity;
import com.jeek.calendar.utils.JeekUtils;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;
import com.jimmy.common.GoalDatabase.Note;
import com.jimmy.common.ItemWrapper;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailAimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private Activity mActivity;
    private Goal mGoal;
    private Aim mAim;
    private List<ItemWrapper> items;

    public DetailAimAdapter(Context context, Goal goal, Aim aim) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        mAim = aim;
        items = aim.getItems();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return new DetailAimAdapter.ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false));
        } else if( viewType == 4){
            Log.wtf("second", "second");
            return new DetailAimAdapter.ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
        }
        Log.wtf("WWWWW", "БЛЯТЬ ТУТ ХУЕВО ПИЗДЕЦ");
        return new DetailAimAdapter.NoteViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailGoalAdapter.ScheduleViewHolder) {
            final GoalSchedule schedule = (GoalSchedule) items.get(position);
            final DetailGoalAdapter.ScheduleViewHolder viewHolder = (DetailGoalAdapter.ScheduleViewHolder) holder;

            //viewHolder.vScheduleHintBlock.setBackgroundColor();
            viewHolder.tvScheduleTitle.setText(schedule.getTitle());
            if (schedule.getTime() != 0) {
                String time_to_display = JeekUtils.timeStamp2Time(schedule.getTime()) + " - " + JeekUtils.timeStamp2Time(schedule.getTime_end());
                viewHolder.tvScheduleTime.setText(time_to_display);
            } else {
                viewHolder.tvScheduleTime.setText("no time");
            }
            viewHolder.vScheduleHintBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("click", "click go to detail Schedule");
                    Intent intent = new Intent(mContext, DetailEventActivity.class).putExtra(DetailEventActivity.SCHEDULE_OBJ, schedule);
                    mActivity.startActivityForResult(intent, 3);
                }
            });
        } else if (holder instanceof DetailAimAdapter.NoteViewHolder){
            Log.wtf("note", "note");
            Note note = (Note) items.get(position);
            final Note NoteFinal = new Note(note.getId(), note.getTitle(), note.getText(), note.getTime());
            final NoteViewHolder viewHolder = (NoteViewHolder) holder;
            if(!note.getTitle().equals("")){
                viewHolder.tvTitle.setVisibility(View.VISIBLE);
                String text = note.getTitle() + ":";
                viewHolder.tvTitle.setText(text);
            }

            Date currentTime = new Date(note.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d   HH:mm");  // TODO CHANGE LOCALE локализировать
            String currentDateandTime = sdf.format(currentTime);
            viewHolder.tvTime.setText(currentDateandTime);
            viewHolder.tvText.setText(note.getText());
            viewHolder.clNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NoteActivity.class).putExtra(DetailAimActivity.GOAL_OBJ, mGoal).putExtra(DetailAimActivity.AIM_OBJ, mAim).putExtra(DetailAimActivity.NOTE_OBJ, NoteFinal);
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

    protected class NoteViewHolder extends RecyclerView.ViewHolder {
        protected ConstraintLayout clNote;
        protected TextView tvTitle, tvText, tvTime;

        public NoteViewHolder(View itemView) {
            super(itemView);
            clNote = (ConstraintLayout) itemView.findViewById(R.id.clNote);
            tvTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            tvTime = (TextView) itemView.findViewById(R.id.tvNoteTime);
            tvText = (TextView) itemView.findViewById(R.id.tvNoteText);
        }
    }

    protected class ListViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout llToolBar;

        public ListViewHolder(View itemView){
            super(itemView);
            llToolBar = itemView.findViewById(R.id.llToolBar);
        }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }



    public void changeAllData(Goal goal, Aim aim) {
        mGoal = goal;
        Log.wtf("wwsad", mGoal.getGoal_name());
        mAim = aim;
        items = aim.getItems();
        notifyDataSetChanged();
    }

}
