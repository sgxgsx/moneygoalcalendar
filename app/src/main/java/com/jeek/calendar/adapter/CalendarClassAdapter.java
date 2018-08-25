package com.jeek.calendar.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.task.CalendarSettingsEntry.ChangeCalendarSettingsEntryValueTask;
import com.jeek.calendar.task.CalendarSettingsEntry.LoadAllCalendarSettingsEntryTask;
import com.jeek.calendar.task.goal.InsertGoalTask;
import com.jimmy.common.CalendarSystemDatabase.CalendarClass;
import com.jimmy.common.GoalDatabase.Aim;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalSchedule;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;
import com.jimmy.common.bean.EventSet;
import com.jeek.calendar.dialog.ConfirmDialog;
import com.jeek.calendar.task.eventset.RemoveEventSetTask;
import com.jeek.calendar.utils.JeekUtils;
import com.jeek.calendar.widget.SlideDeleteView;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jimmy on 2016/10/12 0012.
 */
public class CalendarClassAdapter extends RecyclerView.Adapter<CalendarClassAdapter.CalendarClassViewHolder> {

    private Context mContext;
    private List<CalendarSettingsEntry> mCalendarClasses;

    //TODO DELETE IT

    public CalendarClassAdapter(Context context, List<CalendarSettingsEntry> calendarClasses) {
        mContext = context;
        mCalendarClasses = calendarClasses;
    }

    @Override
    public CalendarClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CalendarClassViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_event_set, parent, false));
    }

    @Override
    public int getItemCount() {
        return mCalendarClasses.size();
    }

    @Override
    public void onBindViewHolder(CalendarClassViewHolder holder, final int position) {
        final CalendarSettingsEntry calendarClass = mCalendarClasses.get(position);

        holder.sdvEventSet.close(false);
        String text = calendarClass.getCalendarName() + String.valueOf(calendarClass.isShow());
        holder.tvEventSetName.setText(text);

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTrueOrFalse(calendarClass);
                Log.wtf("click", "click");
                notifyDataSetChanged();
            }
        });
        /*
        holder.sdvEventSet.setOnContentClickListener(new SlideDeleteView.OnContentClickListener() {
            @Override
            public void onContentClick() {
                gotoEventSetFragment(eventSet);
            }
        });
        */
    }

    private void changeTrueOrFalse(CalendarSettingsEntry calendarClass){
        Log.wtf("Change", "changed");
        new ChangeCalendarSettingsEntryValueTask(mContext, calendarClass).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        List<GoalSchedule> goalSchedules = new ArrayList<>();
        List<Aim> aims = new ArrayList<>();
        for(int i=0; i<5; ++i){
            String title = "Goal " + String.valueOf(i) + "!";
            goalSchedules.add(new GoalSchedule(i, i, title, null, null, true, 0, 0, null));
            aims.add(new Aim(i,"Title aim " + String.valueOf(i), true, goalSchedules));
        }
        Random random = new Random();
        String goal_name = "Goal name " + String.valueOf(random.nextInt());
        String n;
        if(goal_name.length() > 20) {
            n = goal_name.substring(0, 20);
        } else {
            n = goal_name;
        }
        Goal goal = new Goal( n, 10000, aims, goalSchedules);
        new InsertGoalTask(mContext, goal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void showDeleteEventSetDialog(final EventSet eventSet, final int position) {
        new ConfirmDialog(mContext, R.string.event_set_delete_this_event_set, new ConfirmDialog.OnClickListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                new RemoveEventSetTask(mContext, new OnTaskFinishedListener<Boolean>() {
                    @Override
                    public void onTaskFinished(Boolean data) {
                        if (data) {
                            removeItem(position);
                        }
                    }
                }, eventSet.getId()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }).show();
    }
    /*
    private void gotoEventSetFragment(EventSet eventSet) {
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).gotoEventSetFragment(eventSet);
        }
    }
    */
    protected class CalendarClassViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItem;
        private SlideDeleteView sdvEventSet;
        private TextView tvEventSetName;
        private View vEventSetColor;
        private ImageButton ibEventSetDelete;

        public CalendarClassViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.llItem);
            sdvEventSet = (SlideDeleteView) itemView.findViewById(R.id.sdvEventSet);
            vEventSetColor = itemView.findViewById(R.id.vEventSetColor);
            tvEventSetName = (TextView) itemView.findViewById(R.id.tvEventSetName);
            ibEventSetDelete = (ImageButton) itemView.findViewById(R.id.ibEventSetDelete);

        }
    }

    public void changeAllData(List<CalendarSettingsEntry> calendarClasses) {
        mCalendarClasses.clear();
        mCalendarClasses.addAll(calendarClasses);
        notifyDataSetChanged();
    }

    public void insertItem(CalendarSettingsEntry calendarClass) {
        mCalendarClasses.add(calendarClass);
        notifyItemInserted(mCalendarClasses.size() - 1);
    }

    public void removeItem(int position) {
        mCalendarClasses.remove(position);
        notifyDataSetChanged();
    }
}
