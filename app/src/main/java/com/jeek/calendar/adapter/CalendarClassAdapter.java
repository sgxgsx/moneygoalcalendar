package com.jeek.calendar.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.activity.MainActivity;
import com.jimmy.common.bean.CalendarClass;
import com.jimmy.common.bean.EventSet;
import com.jeek.calendar.dialog.ConfirmDialog;
import com.jeek.calendar.task.eventset.RemoveEventSetTask;
import com.jeek.calendar.utils.JeekUtils;
import com.jeek.calendar.widget.SlideDeleteView;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.List;

/**
 * Created by Jimmy on 2016/10/12 0012.
 */
public class CalendarClassAdapter extends RecyclerView.Adapter<CalendarClassAdapter.CalendarClassViewHolder> {

    private Context mContext;
    private List<CalendarClass> mCalendarClasses;

    public CalendarClassAdapter(Context context, List<CalendarClass> calendarClasses) {
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
        final CalendarClass calendarClass = mCalendarClasses.get(position);
        holder.sdvEventSet.close(false);
        holder.tvEventSetName.setText(calendarClass.getDisplayName());
        holder.vEventSetColor.setBackgroundResource(JeekUtils.getEventSetColor(5));
        holder.ibEventSetDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDeleteEventSetDialog(eventSet, position);
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

        private SlideDeleteView sdvEventSet;
        private View vEventSetColor;
        private TextView tvEventSetName;
        private ImageButton ibEventSetDelete;

        public CalendarClassViewHolder(View itemView) {
            super(itemView);
            sdvEventSet = (SlideDeleteView) itemView.findViewById(R.id.sdvEventSet);
            vEventSetColor = itemView.findViewById(R.id.vEventSetColor);
            tvEventSetName = (TextView) itemView.findViewById(R.id.tvEventSetName);
            ibEventSetDelete = (ImageButton) itemView.findViewById(R.id.ibEventSetDelete);
        }
    }

    public void changeAllData(List<CalendarClass> calendarClasses) {
        mCalendarClasses.clear();
        mCalendarClasses.addAll(calendarClasses);
        notifyDataSetChanged();
    }

    public void insertItem(CalendarClass calendarClass) {
        mCalendarClasses.add(calendarClass);
        notifyItemInserted(mCalendarClasses.size() - 1);
    }

    public void removeItem(int position) {
        mCalendarClasses.remove(position);
        notifyDataSetChanged();
    }
}
