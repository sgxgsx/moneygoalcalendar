package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.task.schedule.GetCalendarInfoTask;
import com.jimmy.common.CalendarSystemDatabase.ScheduleDao;
import com.jimmy.common.CalendarSystemDatabase.TupleCalendar;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class SelectCalendarDialog extends Dialog implements OnTaskFinishedListener<String[][]> {
    ScheduleDao mScheduleDao;
    Context mContext;
    private OnSelectCalendarListener mOnSelectCalendarListener;
    private String[][] calendars=mScheduleDao.getAllCalendarsIDsAndNames();


    public SelectCalendarDialog(Context context, SelectCalendarDialog.OnSelectCalendarListener OnSelectCalendarListener) {

        super(context, R.style.DialogFullScreen);
        mOnSelectCalendarListener=OnSelectCalendarListener;
        setContentView(R.layout.dialog_select_calendar);
        mContext=context;
        initView();
    }
    /*public void onAddField(View v) {
        LayoutInflater inflater = getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }*/
    private void initView() {

        ScrollView MainCont = new ScrollView(mContext);
        MainCont.setLayoutParams(new ScrollView.LayoutParams(120,600));


        setContentView(/*R.layout.dialog_select_calendar*/MainCont);
        LinearLayout Main = new LinearLayout(mContext);
        Main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Main.setOrientation(LinearLayout.VERTICAL);
        MainCont.addView(Main);

        new GetCalendarInfoTask(mContext,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        int idd=0;
        for(String[] S:calendars) {
            Log.wtf("Calendar"+idd,calendars[idd][0]+" "+calendars[idd][1]);

            TextView tv=new TextView(mContext);

            final int index = idd;
            tv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Log.wtf("Calendar"+index,calendars[index][0]+" "+calendars[index][1]);

                    mOnSelectCalendarListener.onSelectCalendar(Integer.parseInt(calendars[index][0]),calendars[index][1]);

                    // Code here executes on main thread after user presses button
                }
            });

            tv.setText("Button"+ ++idd);

            Main.addView(tv,100,25);
            if(calendars[idd][0] == null) break;
        }
        Log.wtf("FFF",calendars.toString());
    }



    public interface OnSelectCalendarListener {
        void onSelectCalendar(int id,String name);
    }

    @Override
    public void onTaskFinished(String[][] data) {
        this.calendars=data;
    }


}
