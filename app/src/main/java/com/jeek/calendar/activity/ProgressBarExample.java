package com.jeek.calendar.activity;


import android.app.Activity;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jeek.calendar.R;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressBarExample extends AppCompatActivity {
    Handler handler = new Handler();


    int numberOfLines=50;
    double percentCompleted=0.6;
    int NumberOfLines_completed=(int)(numberOfLines*percentCompleted);
    View[] ProgressBar_Lines=new View[numberOfLines];
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);
        //Fill ProgressBar
        initProgressBar();
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
                ProgressBar_Lines[i].setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rectangle_xx,null));
            }

            Log.wtf("iteration",""+i);
        }


    }






    public void onClickFillProgressBar(View view) {
        Log.wtf("Button","OnClickStarted");
    }



    private void changeConstraints(ConstraintSet set) {
        set.connect(ProgressBar_Lines[0].getId(),ConstraintSet.RIGHT,ProgressBar_Lines[1].getId(),ConstraintSet.RIGHT,0);
    }
}
