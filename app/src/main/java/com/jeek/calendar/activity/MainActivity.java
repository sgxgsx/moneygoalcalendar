package com.jeek.calendar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jeek.calendar.R;
import com.jeek.calendar.fragment.ScheduleFragment;
import com.jeek.calendar.task.CalendarSettingsEntry.AddCalendarTask;
import com.jimmy.common.CalendarSystemDatabase.CalendarClassDao;
import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.base.app.BaseActivity;
import com.jimmy.common.base.app.BaseFragment;
import com.jimmy.common.listener.OnTaskFinishedListener;

import java.util.Calendar;

import butterknife.BindView;
import me.tangke.slidemenu.SlideMenu;


public class MainActivity extends BaseActivity implements View.OnClickListener,OnTaskFinishedListener<Integer> {
    public static String EVENT_SET_OBJ = "event.set.obj";
    public static int ADD_EVENT_SET_CODE = 1;
    public static String ADD_EVENT_SET_ACTION = "action.add.event.set";

    //private DrawerLayout dlMain;
    private LinearLayout llTitleDate;
    private TextView tvTitleMonth, tvTitleDay, tvTitle;
    private View gotoMoneyButton,gotoGoalButton,ChooseMenuButtonBackground;
    private View ChooseModuleButtonTime;
    //private RecyclerView rvMenuCalendarClassList;   //rvMenuEventSetist       CALENDARS
    private SlideMenu slideMenu;
    //private CalendarClassAdapter mCalendarClassAdapter;                       CALENDARS
    //private List<CalendarClass> mCalendarClasses;  // mEventSets              CALENDARS
    private BaseFragment mScheduleFragment;
    //private BaseFragment mEventSetFragment;
    //private EventSet mCurrentEventSet;
    //private AddEventSetBroadcastReceiver mAddEventSetBroadcastReceiver;

    private long[] mNotes = new long[2];
    private String[] mMonthText;
    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;
    //user name
    private static final String ANONYMOUS = "ANONYMOUS";
    private boolean anonym;
    private TextView mUserNameTextView;
    //private FirebaseUser mUser;
    //firebase
    //private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;
    private CalendarClassDao calendarClassDao;
    private OnTaskFinishedListener<Integer> onTaskFinishedListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mUser = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        //check if anonymous
        if(getIntent().hasExtra(ANONYMOUS)){
            mUserNameTextView.setText(ANONYMOUS);
            anonym = true;
        } else{
            anonym = false;
            if (mGoogleSignInAccount != null){
                mUserNameTextView.setText(mGoogleSignInAccount.getEmail());
            }

        }

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //mAuth = FirebaseAuth.getInstance();

        //create default calendar
        //TODO проверить ошибку при которой приложуха закрывается и крашится при условии что календарь уже создан
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 16);
        } else Log.wtf("Permission","Write garandted");


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, 16);

        }
        //todo crashes ?
        //new AddCalendarTask(this,this,23).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        /*
        if (calendarClassDao!= null && !calendarClassDao.defaultCalendarCreated()) {
            Log.wtf("create acc","started main act1");

            calendarClassDao.createDefaultAppCalendar();
        }

*/

    }
    @Override
    protected void bindView() {
        //setContentView(R.layout.activity_main);


        slideMenu = new SlideMenu(this);
        slideMenu.setEdgeSlideEnable(true);

        setContentView(slideMenu);
        LayoutInflater content = getLayoutInflater();
        View contentView = content.inflate(R.layout.activity_main, null);
        // Setup the content

        slideMenu.addView(contentView, new SlideMenu.LayoutParams(
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_CONTENT));


        LayoutInflater contentMenu = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentMenuView = contentMenu.inflate(R.layout.main_menu, null);

        slideMenu.addView(contentMenuView, new SlideMenu.LayoutParams(800,
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_PRIMARY_MENU));




        llTitleDate = searchViewById(R.id.llTitleDate);
        tvTitleMonth = searchViewById(R.id.tvTitleMonth);
        tvTitleDay = searchViewById(R.id.tvTitleDay);
        tvTitle = searchViewById(R.id.tvTitle);
        ChooseModuleButtonTime=searchViewById(R.id.ChooseModuleButtonTime);
        ChooseMenuButtonBackground=searchViewById(R.id.BackGroundWhenChoice);
        ChooseMenuButtonBackground.setVisibility(View.INVISIBLE);
        gotoMoneyButton=searchViewById(R.id.gotoMoneyButton);
        gotoGoalButton=searchViewById(R.id.gotoGoalButton);
        //rvMenuCalendarClassList = searchViewById(R.id.rvMenuEventSetList);
        mUserNameTextView = searchViewById(R.id.tvMenuTitleAccount);
        searchViewById(R.id.ivMainMenu).setOnClickListener(this);
        searchViewById(R.id.llMenuSchedule).setOnClickListener(this);
        //searchViewById(R.id.llMenuNoCategory).setOnClickListener(this);
        searchViewById(R.id.llMenuGoMoney).setOnClickListener(this);

        searchViewById(R.id.llMenuGoGoal).setOnClickListener(this);
        //searchViewById(R.id.tvMenuAddEventSet).setOnClickListener(this);
        searchViewById(R.id.tvMenuSignOut).setOnClickListener(this);
        //searchViewById(R.id.tvMenuDeleteAccount).setOnClickListener(this);
        searchViewById(R.id.tvMenuSettings).setOnClickListener(this);
        searchViewById(R.id.floatingActionButton).setOnClickListener(this);
        searchViewById(R.id.chooseMenuButtonBackground2).setOnClickListener(this);
        searchViewById(R.id.gotoGoalButton).setOnClickListener(this);
        searchViewById(R.id.gotoMoneyButton).setOnClickListener(this);
        searchViewById(R.id.ChooseModuleButtonTime).setOnClickListener(this);
        initUi();
        gotoScheduleFragment();


    }
    private void initUi() {
        mMonthText = getResources().getStringArray(R.array.calendar_month);
        llTitleDate.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        tvTitleMonth.setText(mMonthText[Calendar.getInstance().get(Calendar.MONTH)]);
        tvTitleDay.setText(getString(R.string.calendar_today));
// User name
        if (Build.VERSION.SDK_INT < 19) {
            TextView tvMenuTitle = searchViewById(R.id.tvMenuTitle);
            tvMenuTitle.setGravity(Gravity.CENTER_VERTICAL);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);

    }

    public void resetMainTitleDate(int year, int month, int day) {
        llTitleDate.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        if (year == calendar.get(Calendar.YEAR) &&
                month == calendar.get(Calendar.MONTH) &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            tvTitleMonth.setText(mMonthText[month]);
            tvTitleDay.setText(getString(R.string.calendar_today));
        } else {
            if (year == calendar.get(Calendar.YEAR)) {
                tvTitleMonth.setText(mMonthText[month]);
            } else {
                tvTitleMonth.setText(String.format("%s%s", String.format(getString(R.string.calendar_year), year),
                        mMonthText[month]));
            }
            tvTitleDay.setText(String.format(getString(R.string.calendar_day), day));
        }
        setCurrentSelectDate(year, month, day);
    }

    private void resetTitleText(String name) {
        llTitleDate.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(name);
    }

    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectYear = year;
        mCurrentSelectMonth = month;
        mCurrentSelectDay = day;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMainMenu:
                slideMenu.open(false,true);
                Log.wtf("SlideMenu","   OpenButton");
                break;
            case R.id.llMenuSchedule:
                gotoScheduleFragment();
                break;
            case R.id.llMenuGoMoney:
                gotoMoney();
                break;
            case R.id.llMenuGoGoal:
                gotoGoalFromMenu();
                break;
            case R.id.floatingActionButton:
                gotoEventCreator();
                break;
            case R.id.tvMenuSignOut:
                signOut();
                break;
            /*
            case R.id.tvMenuDeleteAccount:
                if(mUser != null){
                    mUser.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_LONG).show();
                                    gotoAuth();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
                break;
            */
            case R.id.tvMenuSettings:
                gotoSettings();
                break;
            case R.id.ChooseModuleButtonTime:
                showFloatingChoiceMenu();
                break;
            case R.id.gotoMoneyButton:
                gotoMoney();
                break;
            case R.id.gotoGoalButton:
                /*gotoGoal();*/
                gotoProgressBar();
                //gotoChooseDateActivity();
                break;
            case R.id.chooseMenuButtonBackground2:
                hideFloatingChoiceMenu();
                break;
            default:
                break;
        }
    }
    private void signOut() {
        // Firebase sign out
        if(!anonym){
            //mAuth.signOut();

            // Google sign out
            mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        gotoAuth();
                    }
                });
        } else{
            gotoAuth();
        }
    }

    private void showFloatingChoiceMenu() {
        Log.wtf("log", "show menu");
        gotoMoneyButton.setVisibility(View.VISIBLE);
        gotoGoalButton.setVisibility(View.VISIBLE);
        ChooseMenuButtonBackground.setVisibility(View.VISIBLE);
    }
    private void hideFloatingChoiceMenu() {
        Log.wtf("log", "hide menu");
        gotoMoneyButton.setVisibility(View.INVISIBLE);
        gotoGoalButton.setVisibility(View.INVISIBLE);
        ChooseMenuButtonBackground.setVisibility(View.INVISIBLE);

    }

    private void gotoGoalFromMenu(){
        Intent intent = new Intent(this, GoalActivity.class);
        startActivity(intent);
    }

    private void gotoSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    private void gotoAuth(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
    private void gotoEventCreator(){
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    private void gotoMoney(){
        Intent intent = new Intent(this, MoneyActivity.class);
        startActivity(intent);
        gotoMoneyButton.setVisibility(View.INVISIBLE);
        gotoGoalButton.setVisibility(View.INVISIBLE);
        ChooseMenuButtonBackground.setVisibility(View.INVISIBLE);
    }

    private void gotoGoal() {
        //DONE add gotogoal direction
        gotoMoneyButton.setVisibility(View.INVISIBLE);
        gotoGoalButton.setVisibility(View.INVISIBLE);
        ChooseMenuButtonBackground.setVisibility(View.INVISIBLE);
        gotoGoalFromMenu();
    }

    private void gotoProgressBar() {
        Intent intent = new Intent(this, ProgressBarExample.class);
        startActivity(intent);
    }

    private void gotoScheduleFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        if (mScheduleFragment == null) {
            mScheduleFragment = ScheduleFragment.getInstance();
            ft.add(R.id.flMainContainer, mScheduleFragment);
        }

        ft.show(mScheduleFragment);
        ft.commit();
        llTitleDate.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onTaskFinished(Integer data) {
        setResult(1, new Intent().putExtra(EVENT_SET_OBJ, data));
        finish();
    }
}