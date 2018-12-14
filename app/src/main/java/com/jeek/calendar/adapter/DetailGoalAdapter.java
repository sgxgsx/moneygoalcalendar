package com.jeek.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.DetailAimActivity;
import com.jeek.calendar.activity.NoteActivity;
import com.jeek.calendar.utils.JeekUtils;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;
import com.jimmy.common.GoalDatabase.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailGoalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private Activity mActivity;
    private Goal mGoal;
    private List<Aim> aims;
    private List<GoalSchedule> goalSchedules;
    private List<Note> noteList;


    public DetailGoalAdapter(Context context, Goal goal) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        aims = mGoal.getAims();
        goalSchedules = mGoal.getSchedules();
        noteList = mGoal.getNoteList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new AimsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_aim, parent, false));
        } else if (viewType == 2){
            return new ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goal_scheduler, parent, false));
        } else if (viewType == 3){
            Log.wtf("www", "3");
            return new NoteViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false));
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
        if(aims != null || goalSchedules != null || noteList != null){
            if (aims == null){
                if (goalSchedules == null) return 2;
                if (noteList == null) return 3;
                if (position < goalSchedules.size()) return 2;
                return 3;
            }
            if (goalSchedules == null){
                if (aims == null || aims.size() == 0) return 3;
                if (noteList == null) return 1;
                if (position < aims.size()) return 1;
                return 3;
            }
            if (noteList == null){
                if (aims == null || aims.size() == 0) return 2;
                if (goalSchedules == null || goalSchedules.size() == 0) return 1;
                if (position < aims.size()) return 1;
                return 2;
            }
            if (position < aims.size()) return 1;
            if (position < aims.size() + goalSchedules.size()) return 2;
            if (position < aims.size() + goalSchedules.size() + noteList.size()) return 3;
        }
        return 0;
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
            Log.wtf("tag", "ta" + aim.getColor());
            Log.wtf("tag", "ta" + String.valueOf(Color.parseColor(aim.getColor())));
            final DetailGoalAdapter.AimsViewHolder viewHolder = (DetailGoalAdapter.AimsViewHolder) holder;
            viewHolder.tvGoalName.setText(aim.getName());
            Log.wtf("tag", aim.getColor());
            ColorStateList list = ColorStateList.valueOf(Color.parseColor(aim.getColor()));
            viewHolder.clGoal.setBackgroundTintList(list);
            viewHolder.vChange1.setBackgroundTintList(list);
            viewHolder.vChange2.setBackgroundTintList(list);
            viewHolder.llTime.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(aim.getColor()) + 5));
            viewHolder.tvGoalPlannedProgress.setText(String.valueOf(18));
            viewHolder.tvGoalDoneProgress.setText(String.valueOf(1));
            //Date date = new Date();
            //Format format = new SimpleDateFormat("DD.MM.YYYY");
            //viewHolder.tvDateTo.setText(format.format(date));
            viewHolder.tvDateTo.setText("19.10.2018");
            viewHolder.clGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("click", "click go to detail");
                    Intent intent = new Intent(mContext, DetailAimActivity.class).putExtra(DetailAimActivity.GOAL_OBJ, mGoal)
                            .putExtra(DetailAimActivity.AIM_OBJ, aim);
                    mActivity.startActivityForResult(intent, 3);
                }
            });
            /*
            viewHolder.vChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("goto", "Update");
                }
            });
            */
        } else if (holder instanceof NoteViewHolder){
            Log.wtf("vieww holdeeer", "noooooooooooottttttteeeeeeeeeee");
            Note note = null;
            note = noteList.get(position - goalSchedules.size() - aims.size()); // Mogut bit problemi

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
                    Intent intent = new Intent(mContext, NoteActivity.class).putExtra(DetailAimActivity.GOAL_OBJ, mGoal).putExtra(DetailAimActivity.NOTE_OBJ, NoteFinal);
                    mActivity.startActivityForResult(intent, 3);
                }
            });
        }
    }


    protected class AimsViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout clGoal;
        private TextView tvGoalName, tvGoalPlannedProgress, tvGoalDoneProgress, tvDateTo;
        private View vChange1, vChange2;
        private LinearLayout llTime;

        public AimsViewHolder(View itemView) {
            super(itemView);
            clGoal = itemView.findViewById(R.id.clGoal);
            tvGoalName = itemView.findViewById(R.id.tvGoalName);
            tvGoalPlannedProgress = itemView.findViewById(R.id.tvGoalDoneEvents);
            tvGoalDoneProgress = itemView.findViewById(R.id.tvGoalAllEvents);
            tvDateTo = itemView.findViewById(R.id.tvGoalDate);
            vChange1 = itemView.findViewById(R.id.toChange1);
            vChange2 = itemView.findViewById(R.id.toChange2);
            llTime = itemView.findViewById(R.id.AimTime);
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

    @Override
    public int getItemCount() {
        int size = 0;
        if (aims != null) size += aims.size();
        if (goalSchedules != null) size += goalSchedules.size();
        if (noteList != null) size += noteList.size();
        return size;
    }

    public void changeAllData(Goal goal) {
        mGoal = goal;
        Log.wtf("wwsad", mGoal.getGoal_name());
        aims = mGoal.getAims();
        goalSchedules = mGoal.getSchedules();
        noteList = mGoal.getNoteList();
        Log.wtf("wwsad", "here");
        notifyDataSetChanged();
    }

}
