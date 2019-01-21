package com.jeek.calendar.activity;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import com.jeek.calendar.R;

public class DoubleDrawerView extends ViewFlipper {
    private static final int NONE = -1;
    private static final int MAIN_VIEW_INDEX = 0;
    private static final int DRAWER_VIEW_INDEX = 1;
    private static final int DRAWER_VIEW_SECOND_INDEX = 2;
    private static final int DRAWER_MEMBERS = 3;

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

    public void openInnerDrawer() {
        if (getDisplayedChild() != DRAWER_VIEW_INDEX) {
            setChildAndAnimate(DRAWER_VIEW_INDEX, true);
        }
    }

    public void openSecondDrawer(){
        if (getDisplayedChild() != DRAWER_VIEW_SECOND_INDEX){
            Log.wtf("openSecondDrawer", "go");
            setChildAndAnimate(DRAWER_VIEW_SECOND_INDEX, true);
        }
    }

    public void openThirdDrawer(){
        if (getDisplayedChild() != DRAWER_MEMBERS){
            Log.wtf("openThirdDrawer", "3");
            setChildAndAnimate(DRAWER_MEMBERS, true);
        }
    }



    public void closeThirdDrawer(){
        Log.wtf("closeThirdDrawer", "drawer");
        if (getDisplayedChild() != MAIN_VIEW_INDEX || getDisplayedChild() != DRAWER_VIEW_INDEX || getDisplayedChild() != DRAWER_MEMBERS){
            setChildAndAnimate(MAIN_VIEW_INDEX, true);
        }
    }
    public void closeSecondDrawer(){
        if (getDisplayedChild() != MAIN_VIEW_INDEX || getDisplayedChild() != DRAWER_VIEW_INDEX){
            setChildAndAnimate(MAIN_VIEW_INDEX, true);
        }
    }

    public void closeInnerDrawer() {
        if (getDisplayedChild() != MAIN_VIEW_INDEX || getDisplayedChild() != DRAWER_VIEW_SECOND_INDEX) {
            setChildAndAnimate(MAIN_VIEW_INDEX, true);
        }
    }

    public boolean isInnerDrawerOpen() {
        return getDisplayedChild() == DRAWER_VIEW_INDEX;
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
        if (whichChild == DRAWER_VIEW_INDEX || whichChild == DRAWER_VIEW_SECOND_INDEX || whichChild == DRAWER_MEMBERS) {
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


    /*
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.wtf("Save Instance", "save");
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.whichChild = getDisplayedChild();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.wtf("Restore", "restore");
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        Log.wtf("Which", String.valueOf(ss.whichChild));
        setChildAndAnimate(ss.whichChild, false);
    }

    private static class SavedState extends BaseSavedState {
        int whichChild;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            whichChild = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(whichChild);
        }

        public static final Parcelable.Creator<SavedState>
                CREATOR = new Parcelable.Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
    */
}