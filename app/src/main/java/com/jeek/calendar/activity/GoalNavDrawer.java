package com.jeek.calendar.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.jeek.calendar.R;
import com.jeek.calendar.fragment.MembersFragment;

public class GoalNavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MembersFragment.OnFragmentInteractionListener, DrawerLayout.DrawerListener {

    private static final int GO_BACK_CALL_BACK = 1;

    private DrawerLayout drawerLayout;
    private DoubleDrawerView doubleDrawerView;
    private NavigationView mainNavigationView, settingsNavigationView, secondNavigationView;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_goal_nav_drawer);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        doubleDrawerView = (DoubleDrawerView) findViewById(R.id.double_drawer_view);
        mainNavigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        settingsNavigationView = (NavigationView) findViewById(R.id.settings_navigation_view);
        secondNavigationView   = findViewById(R.id.second_navigation_view);

        mainNavigationView.setNavigationItemSelectedListener(this);
        settingsNavigationView.setNavigationItemSelectedListener(this);
        secondNavigationView.setNavigationItemSelectedListener(this);

        drawerLayout.setDrawerListener(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.wtf("post", "post");
    }


    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        Log.wtf("closed", "closed");
        doubleDrawerView.setDisplayedChild(0);
    }

    @Override
    public void onDrawerStateChanged(int i) {
        Log.wtf("state", String.valueOf(i));
    }

    @Override
    public void onFragmentInteraction(int callback) {
        Log.wtf("callback", "callback");
        if(callback == GO_BACK_CALL_BACK){
            doubleDrawerView.closeThirdDrawer();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.wtf("menu", "onNavigationItem");
        switch (item.getItemId()) {
            case R.id.menu_follow:
                doubleDrawerView.openInnerDrawer();
                break;

            case R.id.menu_close_settings:
                doubleDrawerView.closeInnerDrawer();
                break;

            case R.id.menu_screen_3:
                Log.wtf("menu", "open");
                doubleDrawerView.openSecondDrawer();
                break;

            case R.id.menu_screen_1:
                doubleDrawerView.openThirdDrawer();
                break;

            case R.id.fab_add:
                doubleDrawerView.closeSecondDrawer();
                break;

            // Additional cases as needed
            // This example simply Toasts the title for the extra sample items

            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}