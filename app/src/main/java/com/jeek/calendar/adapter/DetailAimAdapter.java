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
import com.jeek.calendar.utils.JeekUtils;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;
import com.jimmy.common.GoalDatabase.Note;

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
    private List<GoalSchedule> goalSchedules;
    private List<Note> noteList;

    public DetailAimAdapter(Context context, Goal goal, Aim aim) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        mAim = aim;
        goalSchedules = mAim.getScheduleList();
        noteList = mAim.getNoteList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new DetailAimAdapter.ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false));
        }
        Log.wtf("WWWWW", "БЛЯТЬ ТУТ ХУЕВО ПИЗДЕЦ");
        return new DetailAimAdapter.NoteViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if(goalSchedules != null || noteList != null){
            if (goalSchedules == null) return 3; //notes
            if (noteList == null) return 1;
            if (position < goalSchedules.size()) return 1;
            return 3;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailGoalAdapter.ScheduleViewHolder) {
            Log.wtf("ID: Schedule", String.valueOf(position) + " and " + String.valueOf(goalSchedules.size()) + " position " + String.valueOf(position));
            final GoalSchedule schedule = goalSchedules.get(position);
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
            Note note = null;
            if(goalSchedules == null){
                note = noteList.get(position);
            } else{
                if (position > goalSchedules.size() - 1){
                    note = noteList.get(position - goalSchedules.size());
                }else {
                    note = noteList.get(position);
                }
            }
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
            //TODO добавить онклики для перехода
            /* добавить ид для лейаута и онклик на него для редактирования.
               улучшить дизайн.
            * */
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
        protected TextView tvTitle, tvText, tvTime;

        public NoteViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            tvTime = (TextView) itemView.findViewById(R.id.tvNoteTime);
            tvText = (TextView) itemView.findViewById(R.id.tvNoteText);
        }
    }

    @Override
    public int getItemCount() {
        //Log.wtf("SIZE", String.valueOf(aims.size() + goalSchedules.size() + 2) + " or " + String.valueOf(aims.size() + goalSchedules.size()));
        int size = 0;
        if(goalSchedules != null) size += goalSchedules.size();
        if(noteList != null) size += noteList.size();
        return size;
    }


    public void changeAllData(Goal goal, Aim aim) {
        mGoal = goal;
        Log.wtf("wwsad", mGoal.getGoal_name());
        mAim = aim;
        goalSchedules = mAim.getScheduleList();
        noteList = mAim.getNoteList();
        Log.wtf("wwsad", "here");
        notifyDataSetChanged();
    }

}
