package com.jeek.calendar.activity;


import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.ag.floatingactionmenu.OptionsFabLayout;
import com.jeek.calendar.R;
import com.jeek.calendar.dialog.AddEventDialog;
import com.jeek.calendar.dialog.SelectCalendarDialog;
import com.jimmy.common.base.app.BaseActivity;
import com.jimmy.common.listener.BooVariable;


public class ProgressBarExample extends BaseActivity implements  View.OnClickListener,AddEventDialog.OnAddEventListener, SelectCalendarDialog.OnSelectCalendarListener {
    Context context;
    Handler handler = new Handler();
Bundle saved;
    private AddEventDialog mAddEventDialog;
private SelectCalendarDialog mSelectCalendarDialog;
    OptionsFabLayout fabWithOptions;
    int numberOfLines=50;
    double percentCompleted=0.6;
    int marginInDp=6;
    boolean isFABOpen=false;
    int marginInPixels;/*=(int)pxFromDp(ProgressBarExample.this,marginInDp);*/
    int NumberOfLines_completed=(int)(numberOfLines*percentCompleted);
    View[] ProgressBar_Lines=new View[numberOfLines];
    Context mContext;
    FloatingActionButton fab1,fab2,fab3;
@Override
protected void bindView() {
    setContentView(R.layout.progress_bar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab1 = (FloatingActionButton) findViewById(R.id.fab1);
    fab2 = (FloatingActionButton) findViewById(R.id.fab2);
    fab3 = (FloatingActionButton) findViewById(R.id.fab3);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!isFABOpen){
                showFABMenu();
            }else{
                closeFABMenu();
            }
        }
    });
    //findViewById(R.id.fab_l).setOnClickListener(this);
    /*fabWithOptions = (OptionsFabLayout) findViewById(R.id.fab_l);
    fabWithOptions.setLayoutParams(new ViewGroup.LayoutParams());



    BooVariable d =new BooVariable(); d.setBoo(fabWithOptions.isOptionsMenuOpened());
    d.setListener(new BooVariable.ChangeListener() {
        @Override
        public void onChange() {

        }
    });
    fabWithOptions.setMainFabOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            makeToast(1);
        }
    });
    fabWithOptions.setMiniFabSelectedListener(new OptionsFabLayout.OnMiniFabSelectedListener() {
        @Override
        public void onMiniFabSelected(MenuItem fabItem) {
            switch (fabItem.getItemId()) {
                case R.id.fab_add:
                    Toast.makeText(
                            getApplicationContext(),
                            fabItem.getTitle() + " clicked!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fab_link:
                    Toast.makeText(getApplicationContext(),
                            fabItem.getTitle() + " clicked!",
                            Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    });
*/


}


    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void makeToast(int i){                if(i==1)Toast.makeText(this, "Main fab clicked!", Toast.LENGTH_SHORT).show();
    else Toast.makeText(this, "Sup fab clicked!", Toast.LENGTH_SHORT).show();
}

    public void initProgressBar() {

        LayoutInflater mLayoutInflater = getLayoutInflater();
        LinearLayout rootLayout = findViewById(R.id.progressBarMain);



        for (int i=0;i<50;i++) {



            if(i<NumberOfLines_completed) {
                ProgressBar_Lines[i] =  mLayoutInflater.inflate(R.layout.item_progressbar_line_green,rootLayout);

            } else {
                ProgressBar_Lines[i] =  mLayoutInflater.inflate(R.layout.item_progressbar_line,rootLayout);
                //todo EBAT ZARABOTALO DODELAT'
                //PS:to set drawable resource to view, you need to delete drawable from first resource
                ProgressBar_Lines[i].setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rectangle_completed_part,null));
            }

            Log.wtf("iteration",""+i);
        }


    }


    public void initProgressBar_2(){
        LayoutInflater mLayoutInflater = getLayoutInflater();
        LinearLayout rootLayout = findViewById(R.id.progressBarMain);
        marginInPixels=(int)pxFromDp(ProgressBarExample.this,marginInDp);
        Toast toast = Toast.makeText(this, ""+marginInPixels, Toast.LENGTH_SHORT);
        toast.show();

        if (NumberOfLines_completed>=2){
            for(int i=0;i<NumberOfLines_completed;++i){
                ProgressBar_Lines[i] =  mLayoutInflater.inflate(R.layout.item_progressbar_line_green,rootLayout);
                LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)ProgressBar_Lines[i].getLayoutParams();
                params.setMargins(marginInPixels*i,0,0,0);
                ProgressBar_Lines[i].setLayoutParams(params);
                Log.wtf("iter=",""+i);
            }
            for(int i=NumberOfLines_completed;i<50;++i){
                ProgressBar_Lines[i] =  mLayoutInflater.inflate(R.layout.item_progressbar_line,rootLayout);
                LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)ProgressBar_Lines[i].getLayoutParams();
                params.setMargins(marginInPixels*i,0,0,0);
                ProgressBar_Lines[i].setLayoutParams(params);
                Log.wtf("iter=",""+i);
            }
        }
        else {if(NumberOfLines_completed==1){
            ProgressBar_Lines[0] =  mLayoutInflater.inflate(R.layout.item_progressbar_line_green,rootLayout);
            for(int i=1;i<50;++i){
                ProgressBar_Lines[i] =  mLayoutInflater.inflate(R.layout.item_progressbar_line,rootLayout);
                LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)ProgressBar_Lines[i].getLayoutParams();
                params.setMargins(marginInPixels*i,0,0,0);
                ProgressBar_Lines[i].setLayoutParams(params);
                Log.wtf("iter=",""+i);
            }
        }
        else {for(int i =0;i<50;++i){
            ProgressBar_Lines[i] =  mLayoutInflater.inflate(R.layout.item_progressbar_line,rootLayout);
            LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)ProgressBar_Lines[i].getLayoutParams();
            params.setMargins(marginInPixels*i,0,0,0);
            ProgressBar_Lines[i].setLayoutParams(params);
            Log.wtf("iter=",""+i);
        }}
        }
    }

    public void initProgressBar_3(){
        LayoutInflater mLayoutInflater = getLayoutInflater();
        LinearLayout rootLayout = findViewById(R.id.progressBarMain);
        View CompletedLine= mLayoutInflater.inflate(R.layout.item_progressbar_line_green,rootLayout);
        LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams) CompletedLine.getLayoutParams();
        /*loparams.weight=*/



    }





    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    public void onClickFillProgressBarD(View view) {
        showAddEventDialog();
    }



    private void showSelectCalendarDialog(){
        if (mSelectCalendarDialog==null){
            mSelectCalendarDialog = new SelectCalendarDialog(this,this);

        }
        mSelectCalendarDialog.show();
    }

    private void showAddEventDialog() {
        if (mAddEventDialog == null) {
            mAddEventDialog = new AddEventDialog(this, this,saved);
        }
        mAddEventDialog.show();
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    private Runnable ProgressBarAnimation = new Runnable() {
        @Override
        public void run() {
            // Do something here on the main thread
            Log.d("Handlers", "Called on main thread");
        }
    };

    @Override
    public void OnAddEvent(String text) {
    }

    @Override
    public void onSelectCalendar(int id,String name) {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.fab_l:
                break;*/

            default:
                break;
        }
    }
}
