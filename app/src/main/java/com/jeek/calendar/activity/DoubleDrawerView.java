package com.jeek.calendar.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.jeek.calendar.R;

public class DoubleDrawerView extends ViewFlipper {
    private static final int NONE = -1;
    private static final int MAIN_VIEW_INDEX = 0;
    private static final int DRAWER_MEMBERS = 1;
    private static final int DRAWER_ACTIVITY = 2;
    private static final int DRAWER_ADDONS = 3;
    private static final int DRAWER_ARCHIVED_TASKS = 4;
    private static final int DRAWER_ARCHIVED_LISTS = 5;
    private static final int DRAWER_GOAL_SETTINGS = 6;

    private Animation slideInAnimation, slideOutAnimation, noAnimation;
    private boolean animating = false;

    private Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation anim) {
            animating = false;
        }

        @Override
        public void onAnimationStart(Animation anim) {
        }

        @Override
        public void onAnimationRepeat(Animation anim) {
        }
    };

    public DoubleDrawerView(Context context) {
        this(context, null);
    }

    public DoubleDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        slideOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        noAnimation = AnimationUtils.loadAnimation(context, R.anim.none);
        noAnimation.setAnimationListener(listener);
    }

    public void openMembers() {
        if (getDisplayedChild() != DRAWER_MEMBERS) {
            setChildAndAnimate(DRAWER_MEMBERS, true);
        }
    }

    public void openActivity() {
        if (getDisplayedChild() != DRAWER_ACTIVITY) {
            setChildAndAnimate(DRAWER_ACTIVITY, true);
        }
    }

    public void openAddons() {
        if (getDisplayedChild() != DRAWER_ADDONS) {
            setChildAndAnimate(DRAWER_ADDONS, true);
        }
    }

    public void openArchivedTasks() {
        if (getDisplayedChild() != DRAWER_ARCHIVED_TASKS) {
            setChildAndAnimate(DRAWER_ARCHIVED_TASKS, true);
        }
    }

    public void openArchivedLists() {
        if (getDisplayedChild() != DRAWER_ARCHIVED_LISTS) {
            setChildAndAnimate(DRAWER_ARCHIVED_LISTS, true);
        }
    }

    public void openGoalSettings() {
        if (getDisplayedChild() != DRAWER_GOAL_SETTINGS) {
            setChildAndAnimate(DRAWER_GOAL_SETTINGS, true);
        }
    }


    public void closeFragment() {
        Log.wtf("closeThirdDrawer", "drawer");
        setChildAndAnimate(MAIN_VIEW_INDEX, true);
    }


    public boolean isInnerDrawerOpen() {
        // shitty method, maybe will be deleted. idk
        return getDisplayedChild() == DRAWER_MEMBERS;
    }

    private void setChildAndAnimate(int whichChild, boolean doAnimate) {
        Log.wtf("animate", "do");
        if (doAnimate) {
            Log.wtf("animate", "animate");
            setAnimationForChild(whichChild);
        } else {
            setAnimationForChild(NONE);
        }
        animating = doAnimate;
        Log.wtf("set displayed child", String.valueOf(whichChild));
        setDisplayedChild(whichChild);
    }

    private void setAnimationForChild(int whichChild) {
        if (whichChild == DRAWER_MEMBERS || whichChild == DRAWER_ACTIVITY || whichChild == DRAWER_ADDONS || whichChild == DRAWER_ARCHIVED_TASKS || whichChild == DRAWER_ARCHIVED_LISTS || whichChild == DRAWER_GOAL_SETTINGS) {
            setInAnimation(slideInAnimation);
            setOutAnimation(noAnimation);
        } else if (whichChild == MAIN_VIEW_INDEX) {
            setInAnimation(noAnimation);
            setOutAnimation(slideOutAnimation);
        } else {
            Log.wtf("NUll", "null");
            setInAnimation(null);
            setOutAnimation(null);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (animating) {
            Log.wtf("touch", "anim");
            return true;
        } else {
            Log.wtf("touch", "event");
            return super.onInterceptTouchEvent(ev);
        }
    }


    public void checkedChanged(boolean checked) {
        Log.wtf("checked", "checked in doubleDrawerView");
    }

}