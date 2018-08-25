package com.jeek.calendar.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.provider.ContactsContract;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jeek.calendar.adapter.CalendarClassAdapter;
import com.jeek.calendar.task.CalendarSettingsEntry.LoadAllCalendarSettingsEntryTask;
import com.jeek.calendar.task.calendarclass.LoadCalendarClassesTask;
import com.jeek.calendar.task.goal.InsertGoalTask;
import com.jimmy.common.CalendarSystemDatabase.CalendarClassDao;
import com.jimmy.common.GoalDatabase.Goal;
import com.jimmy.common.GoalDatabase.GoalDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsDatabase;
import com.jimmy.common.SettingsDatabase.CalendarSettingsEntry;
import com.jimmy.common.base.app.BaseActivity;
import com.jimmy.common.CalendarSystemDatabase.CalendarClass;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SettingsActivity extends BaseActivity implements /*View.OnClickListener,*/ OnTaskFinishedListener<List<CalendarSettingsEntry>>,  SharedPreferences.OnSharedPreferenceChangeListener {

    private Toolbar mToolBar;
    private TextView mToolBarTextViewTitle;

    private RecyclerView rvMenuCalendarClassList;   //rvMenuEventSetist      CALENDARS

    private CalendarClassAdapter mCalendarClassAdapter;
    //private List<CalendarClass> mCalendarClasses;
    private List<CalendarSettingsEntry> mCalendarSettingsEntry;


    @Override
    public void onCreate(Bundle onSaved)
    {
        super.onCreate(onSaved);
        //mCalendarSettingsEntry = CalendarClassDao.getInstance(this).getAllCalendars();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_settings);

        mToolBar = searchViewById(R.id.tbSettings);
        mToolBarTextViewTitle = searchViewById(R.id.tvSettingsTitle);
        rvMenuCalendarClassList = searchViewById(R.id.rvSettingsCalendarClasses);
        initCalendarClassesList();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }
    private void initCalendarClassesList() {
        mCalendarSettingsEntry = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenuCalendarClassList.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvMenuCalendarClassList.setItemAnimator(itemAnimator);
        mCalendarClassAdapter = new CalendarClassAdapter(this, mCalendarSettingsEntry);
        rvMenuCalendarClassList.setAdapter(mCalendarClassAdapter);
    }



    @Override
    protected void initData() {
        super.initData();
        new LoadAllCalendarSettingsEntryTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        new AsyncTask<Void,Void,List<Goal>>(){

            @Override
            protected List<Goal> doInBackground(Void... voids) {
                List<Goal> goals = GoalDatabase.getInstance(getApplicationContext()).goalDao().loadGoals();
                if(goals == null){
                    Log.wtf("NUll", "null goals");
                } else{
                    for(int i=0; i < goals.size(); ++i){
                        Goal goal = goals.get(i);
                        Log.wtf("goal", goal.getGoal_name());
                    }
                }
                Log.wtf("goals", goals.toString());
                return goals;
            }

            @Override
            protected void onPostExecute(List<Goal> goals) {
                super.onPostExecute(goals);
                if(goals == null){
                    Log.wtf("NUll", "null goals");
                } else{

                }
                Log.wtf("goals", goals.toString());
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onTaskFinished(List<CalendarSettingsEntry> data) {
        mCalendarClassAdapter.changeAllData(data);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        if (key.equals(getString(R.string.pref_changed_value_calendars))){
            Log.wtf("Tag", "changed");
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    /*
    private void initBroadcastReceiver() {
        if (mAddEventSetBroadcastReceiver == null) {
            mAddEventSetBroadcastReceiver = new AddEventSetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ADD_EVENT_SET_ACTION);
            registerReceiver(mAddEventSetBroadcastReceiver, filter);
        }
    }
*/
}
