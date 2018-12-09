package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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

    public String[][] calendars = new String[100][100];
/*
    private String[][] calendars={{"1","da"},{"2","db"},{"3","dc"}};
*/


    public SelectCalendarDialog(Context context, SelectCalendarDialog.OnSelectCalendarListener OnSelectCalendarListener) {

        super(context, R.style.DialogFullScreen);

        mOnSelectCalendarListener=OnSelectCalendarListener;
        setContentView(R.layout.dialog_select_calendar);
        mContext=context;
        new GetCalendarInfoTask(mContext,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
        //todo ya hz ono ne uspevaet zakonchit' task pered vivodom
        new GetCalendarInfoTask(mContext,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        TextView[] tv =new TextView[20];
        int idd=0;
        for(String[] S:calendars) {
            Log.wtf("Calendar"+idd,calendars[idd][0]+" "+calendars[idd][1]);

            tv[idd]= new TextView(mContext);
            tv[idd].setText("id:"+calendars[idd][0]+" name:"+calendars[idd][1]);
            Log.wtf("TDTDTDTD",calendars[idd][0]+" "+calendars[idd][1]);
            tv[idd].setBackgroundColor(Color.BLACK);
            tv[idd].setTextColor(Color.WHITE);
            tv[idd].setGravity(Gravity.CENTER);
            final int index = idd;
            final TextView tt = tv[idd];
            tv[idd].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Log.wtf("Calendar "+index,calendars[index][0]+" "+calendars[index][1]);

                    mOnSelectCalendarListener.onSelectCalendar(Integer.parseInt(calendars[index][0]),calendars[index][1]);

                }
            });

            Main.addView(tt,400,70);

            idd++;
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
