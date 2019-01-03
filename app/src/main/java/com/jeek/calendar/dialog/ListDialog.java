package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.jeek.calendar.R;

public class ListDialog extends Dialog{

    private OnListListener mOnListListener;
    private SharedPreferences sharedPreferences;
    private Resources mResources;
    private CheckBox cbDoing, cbDone;
    private boolean mDoing, mDone;
    private String SHARED_DOING, SHARED_DONE;

    public ListDialog(Context context, OnListListener onListListener) {
        super(context, R.style.DialogFullScreen);
        mOnListListener = onListListener;
        setContentView(R.layout.dialog_list);


        cbDoing = findViewById(R.id.cbDoing);
        cbDone = findViewById(R.id.cbDone);
        mResources = context.getResources();
        SHARED_DOING = mResources.getString(R.string.shared_doing_goals);
        SHARED_DONE  = mResources.getString(R.string.shared_done_goals);

        sharedPreferences = context.getSharedPreferences(mResources.getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
    }

    @Override
    public void show() {
        super.show();
        start();
    }

    public void start(){
        mDoing = sharedPreferences.getBoolean(SHARED_DOING, true);
        mDone  = sharedPreferences.getBoolean(SHARED_DONE,true);
    }
    public interface OnListListener {
        void onList(boolean changed, boolean doing, boolean done);
    }

    @Override
    public void dismiss() {
        boolean doing = cbDoing.isChecked();
        boolean done  = cbDone.isChecked();

        if(doing != mDoing || done != mDone){
            mOnListListener.onList(true, doing, done);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SHARED_DOING, doing);
            editor.putBoolean(SHARED_DONE, done);
            editor.apply();
        }
        super.dismiss();
    }

    // TODO LIST DIALOG
    /*
        1. сделать лейаут диалога 2 - ентри
        2. сделать Энтри в БД или БД для этого ????????????
        3. добавить Гоалу поле / к какому листу относится /
        4. сделать 2 дефолтных листа - DOING / DONE
        6. дать возможность добавлять гоал либо в один либо в другой лист
        7. дать возможность показывать определенный лист

     */
}