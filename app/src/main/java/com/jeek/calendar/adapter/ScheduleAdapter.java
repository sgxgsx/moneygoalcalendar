
package com.jeek.calendar.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.DetailEventActivity;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jeek.calendar.fragment.ScheduleFragment;
import com.jeek.calendar.task.schedule.RemoveScheduleTask;
import com.jeek.calendar.task.schedule.UpdateScheduleTask;
import com.jeek.calendar.utils.JeekUtils;
import com.jeek.calendar.widget.StrikeThruTextView;
import com.jimmy.common.base.app.BaseFragment;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 2016/10/8 0008.
 */
public class  ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int SCHEDULE_TYPE = 1;

    private Context mContext;
    private BaseFragment mBaseFragment;
    private List<Schedule> mSchedules;
    private List<Schedule> mFinishSchedules;

    public ScheduleAdapter(Context context, BaseFragment baseFragment) {
        mContext = context;
        mBaseFragment = baseFragment;
        initData();
    }

    private void initData() {
        mSchedules = new ArrayList<Schedule>();
        mFinishSchedules = new ArrayList<Schedule>();
        Log.wtf("SchedAdapt","Inited");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SCHEDULE_TYPE) {
            Log.wtf("onCreateViewHolder","Item_schedule inserting");
            return new ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false));
        }
        return new ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScheduleViewHolder) {
            final Schedule schedule = mSchedules.get(position);
            final ScheduleViewHolder viewHolder = (ScheduleViewHolder) holder;
            //viewHolder.vScheduleHintBlock.setBackgroundColor(schedule.getColor());
            viewHolder.vScheduleHintBlock.setBackgroundTintList(ColorStateList.valueOf(schedule.getColor()));
            viewHolder.tvScheduleTitle.setText(schedule.getTitle());
            if (schedule.getTime() != 0) {
                String time_to_display = JeekUtils.timeStamp2Time(schedule.getTime()) + " - " + JeekUtils.timeStamp2Time(schedule.getTime_end());
                viewHolder.tvScheduleTime.setText(time_to_display);
            } else {
                viewHolder.tvScheduleTime.setText("");
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, DetailEventActivity.class).putExtra(DetailEventActivity.SCHEDULE_OBJ, schedule));

                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position < mSchedules.size()) {
            return SCHEDULE_TYPE;
        }
        return SCHEDULE_TYPE;
    }

    @Override
    public int getItemCount() {
        return mSchedules.size();
    }

    protected class ScheduleViewHolder extends RecyclerView.ViewHolder {

        protected ConstraintLayout vScheduleHintBlock;
        //protected TextView tvScheduleState;
        protected TextView tvScheduleTitle;
        protected TextView tvScheduleTime;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            vScheduleHintBlock = (ConstraintLayout) itemView.findViewById(R.id.vScheduleHintBlock);
            //tvScheduleState = (TextView) itemView.findViewById(R.id.tvScheduleState);
            tvScheduleTitle = (TextView) itemView.findViewById(R.id.tvScheduleTitle);
            tvScheduleTime = (TextView) itemView.findViewById(R.id.tvScheduleTime);
        }

    }


    public void changeAllData(List<Schedule> schedules) {
        distinguishData(schedules);
        Log.wtf("changeAllData","end");
    }

    public void insertItem(Schedule schedule) {
        Log.wtf("InsertItem","start");
        mSchedules.add(schedule);
        notifyItemInserted(mSchedules.size() - 1);
    }

    public void removeItem(Schedule schedule) {
        if (mSchedules.remove(schedule)) {
            notifyDataSetChanged();
        } else if (mFinishSchedules.remove(schedule)) {
            notifyDataSetChanged();
        }
    }


    private void distinguishData(List<Schedule> schedules) {
        mSchedules.clear();
        mFinishSchedules.clear();
        for (int i = 0, count = schedules.size(); i < count; i++) {
            Log.wtf("distinguishData","iter = "+i);

            Schedule schedule = schedules.get(i);
            if (schedule.getState() == 2) {
                mFinishSchedules.add(schedule);
            } else {
                mSchedules.add(schedule);
            }
        }
        notifyDataSetChanged();
    }

}