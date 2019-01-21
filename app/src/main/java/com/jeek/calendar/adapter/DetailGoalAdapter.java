package com.jeek.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.jimmy.common.ItemWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailGoalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private Resources mResources;
    private Activity mActivity;
    private Goal mGoal;
    private List<ItemWrapper> items;

    public DetailGoalAdapter(Context context, Goal goal) {
        mContext = context;
        mActivity = (Activity) mContext;
        mGoal = goal;
        items = goal.getItems();
        mResources = context.getResources();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.wtf("view type", String.valueOf(viewType));
        if (viewType == 1) {
            return new AimsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_aim, parent, false));
        } else if (viewType == 2){
            return new ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goal_scheduler, parent, false));
        } else if (viewType == 3){
            return new NoteViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false));
        }
        return new DetailGoalAdapter.AimsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_aim, parent, false));
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
            final Aim aim = (Aim) items.get(position);
            final DetailGoalAdapter.AimsViewHolder viewHolder = (DetailGoalAdapter.AimsViewHolder) holder;
            viewHolder.tvGoalName.setText(aim.getName());
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
        } else if (holder instanceof NoteViewHolder){
            Note note = (Note) items.get(position);

            final Note NoteFinal = new Note(note.getId(), note.getTitle(), note.getText(), note.getTime());
            final NoteViewHolder viewHolder = (NoteViewHolder) holder;
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(getInPx(8),     getInPx(10), getInPx(8), getInPx(4));
            viewHolder.clNote.setLayoutParams(layoutParams);

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

    private int getInPx(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mResources.getDisplayMetrics());
    }

    public class AimsViewHolder extends RecyclerView.ViewHolder {
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

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout vScheduleHintBlock;
        public LinearLayout llScheduleState;
        public TextView tvScheduleTitle;
        public TextView tvScheduleTime;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            vScheduleHintBlock = itemView.findViewById(R.id.vScheduleHintBlock);
            llScheduleState = itemView.findViewById(R.id.llScheduleStateGoalScheduler);
            tvScheduleTitle = itemView.findViewById(R.id.tvScheduleTitle);
            tvScheduleTime = itemView.findViewById(R.id.tvScheduleTime);
        }
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout clNote;
        public TextView tvTitle, tvText, tvTime;

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
        return items.size();
    }

    public void changeAllData(Goal goal) {
        mGoal = goal;
        items = goal.getItems();
        Log.wtf("repeat", "repeat");
        notifyDataSetChanged();
    }

}
