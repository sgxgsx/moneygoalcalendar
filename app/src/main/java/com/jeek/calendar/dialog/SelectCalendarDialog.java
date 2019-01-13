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


public class SelectCalendarDialog extends Dialog {
    Context mContext;
    private OnSelectCalendarListener mOnSelectCalendarListener;

    String[][] calendars=new String[50][50];


    public SelectCalendarDialog(Context context, SelectCalendarDialog.OnSelectCalendarListener OnSelectCalendarListener,String[][] cals) {

        super(context, R.style.DialogFullScreen);

        mOnSelectCalendarListener=OnSelectCalendarListener;
        setContentView(R.layout.dialog_select_calendar);
        mContext=context;
        //new GetCalendarInfoTask(mContext,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        initView();
        this.calendars=cals;
    }
    private void initView() {
        Log.wtf("HEH",""+calendars[0][0]+" "+calendars[0][1]);
        ScrollView MainCont = new ScrollView(mContext);
        MainCont.setLayoutParams(new ScrollView.LayoutParams(120,600));



        LinearLayout Main = new LinearLayout(mContext);
        Main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Main.setOrientation(LinearLayout.VERTICAL);
        MainCont.addView(Main);

        TextView[] tv =new TextView[20];
        int idd=0;
        for(String[] S:calendars) {
            Log.wtf("Calendar"+idd,S[idd]);

            tv[idd]= new TextView(mContext);
            tv[idd].setText("name:"+S[idd]);
            Log.wtf("TDTDTDTD",S[idd]);
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
        setContentView(MainCont);
    }



    public interface OnSelectCalendarListener {
        void onSelectCalendar(int id,String name);
    }


}
