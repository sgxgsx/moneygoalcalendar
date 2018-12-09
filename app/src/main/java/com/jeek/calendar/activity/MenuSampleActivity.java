package com.jeek.calendar.activity;


import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeek.calendar.R;
import com.jeek.calendar.dialog.AddEventDialog;
import com.jeek.calendar.dialog.SelectCalendarDialog;
import com.jimmy.common.base.app.BaseActivity;

import me.tangke.slidemenu.SlideMenu;


public class MenuSampleActivity extends Activity{


    @Override
    protected void onCreate(Bundle state) {
        //setContentView(R.layout.activity_menu_sample);
        super.onCreate(state);
        SlideMenu slideMenu = new SlideMenu(this);
        setContentView(slideMenu);
        LayoutInflater content = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = content.inflate(R.layout.activity_menu_sample, null);
        // Setup the content
        //View contentView = new View(this);
        slideMenu.addView(contentView, new SlideMenu.LayoutParams(
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_CONTENT));

        // Setup the primary menu
        /*LinearLayout primaryMenu = new LinearLayout(this);
        primaryMenu.setBackgroundColor(Color.BLUE);
        primaryMenu.setOrientation(LinearLayout.VERTICAL);
        TextView tv1 = new TextView(this);
        tv1.setText("TEXTVIEW1");
        tv1.setBackgroundColor(Color.GREEN);
        tv1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             Log.wtf("menu1","onClick");

            }
        });
        TextView tv2 = new TextView(this);
        tv2.setText("TEXTVIEW2");
        tv2.setBackgroundColor(Color.GREEN);
        tv2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.wtf("menu2","onClick");

            }
        });
        TextView tv3 = new TextView(this);
        tv3.setText("TEXTVIEW3");
        tv3.setBackgroundColor(Color.GREEN);
        tv3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.wtf("menu3","onClick");

            }
        });
        TextView tv4 = new TextView(this);
        tv4.setText("TEXTVIEW4");
        tv4.setBackgroundColor(Color.GREEN);
        tv4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.wtf("menu4","onClick");

            }
        });
        primaryMenu.addView(tv1,800,75);
        primaryMenu.addView(tv2,800,75);
        primaryMenu.addView(tv3,800,75);
        primaryMenu.addView(tv4,800,75);*/
        LayoutInflater contentMenu = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentMenuView = contentMenu.inflate(R.layout.main_menu, null);

        slideMenu.addView(contentMenuView, new SlideMenu.LayoutParams(800,
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_PRIMARY_MENU));


    }



}
