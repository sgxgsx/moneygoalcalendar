package com.jeek.calendar.activity;


import android.app.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jeek.calendar.R;

public class ProgressBarExample extends AppCompatActivity {
    Context context;
    Handler handler = new Handler();


    int numberOfLines=50;
    double percentCompleted=0.6;
    int marginInDp=6;
    int marginInPixels;/*=(int)pxFromDp(ProgressBarExample.this,marginInDp);*/
    int NumberOfLines_completed=(int)(numberOfLines*percentCompleted);
    View[] ProgressBar_Lines=new View[numberOfLines];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);
        //Fill ProgressBar
        initProgressBar_2();
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


    public void onClickFillProgressBar(View view) {



        Log.wtf("Button","OnClickStarted");
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



}
