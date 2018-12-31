package com.jeek.calendar.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.task.CalendarSettingsEntry.ChangeCalendarSettingsEntryValueTask;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;
import com.jimmy.common.bean.EventSet;
import com.jeek.calendar.task.eventset.RemoveEventSetTask;
import com.jeek.calendar.widget.SlideDeleteView;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.List;

/**
 * Created by Jimmy on 2016/10/12 0012.
 */
public class CalendarClassAdapter extends RecyclerView.Adapter<CalendarClassAdapter.CalendarClassViewHolder> {

    private Context mContext;
    private List<CalendarSettingsEntry> mCalendarClasses;

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
    }

    private void changeTrueOrFalse(CalendarSettingsEntry calendarClass){
        Log.wtf("Change", "changed");
        new ChangeCalendarSettingsEntryValueTask(mContext, calendarClass).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    protected class CalendarClassViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItem;
        private SlideDeleteView sdvEventSet;
        private TextView tvEventSetName;

        public CalendarClassViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.llItem);
            sdvEventSet = (SlideDeleteView) itemView.findViewById(R.id.sdvEventSet);
            tvEventSetName = (TextView) itemView.findViewById(R.id.tvEventSetName);

        }
    }

    public void changeAllData(List<CalendarSettingsEntry> calendarClasses) {
        mCalendarClasses.clear();
        mCalendarClasses.addAll(calendarClasses);
        notifyDataSetChanged();
    }
}
