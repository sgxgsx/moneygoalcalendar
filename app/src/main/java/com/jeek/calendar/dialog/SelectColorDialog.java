package com.jeek.calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.jeek.calendar.R;

/**
 * Created by Jimmy on 2016/10/12 0012.
 */
public class SelectColorDialog extends Dialog implements View.OnClickListener {

    private OnSelectColorListener mOnSelectColorListener;
    private String mColor;

    public SelectColorDialog(Context context, OnSelectColorListener onSelectColorListener) {
        super(context, R.style.DialogFullScreen);
        mOnSelectColorListener = onSelectColorListener;
        setContentView(R.layout.dialog_select_color);
        initView();
    }

    private void initView() {
        findViewById(R.id.dsc_default).setOnClickListener(this);
        findViewById(R.id.dsc_banana).setOnClickListener(this);
        findViewById(R.id.dsc_tangerine).setOnClickListener(this);
        findViewById(R.id.dsc_tomato).setOnClickListener(this);
        findViewById(R.id.dsc_basil).setOnClickListener(this);
        findViewById(R.id.dsc_sage).setOnClickListener(this);
        findViewById(R.id.dsc_peacock).setOnClickListener(this);
        findViewById(R.id.dsc_blueberry).setOnClickListener(this);
        findViewById(R.id.dsc_lavender).setOnClickListener(this);
        findViewById(R.id.dsc_grape).setOnClickListener(this);
        findViewById(R.id.dsc_flamingo).setOnClickListener(this);
        findViewById(R.id.dsc_graphite).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dsc_default:
                mColor = "#0073e6";
                break;
            case R.id.dsc_banana:
                mColor = "#ffe135";
                break;
            case R.id.dsc_tangerine:
                mColor = "#f28500";
                break;
            case R.id.dsc_tomato:
                mColor = "#ff6347";
                break;
            case R.id.dsc_basil:
                mColor = "#005600";
                break;
            case R.id.dsc_sage:
                mColor = "#00a300";
                break;
            case R.id.dsc_peacock:
                mColor = "#008e94";
                break;
            case R.id.dsc_blueberry:
                mColor = "#2b3d71";
                break;
            case R.id.dsc_lavender:
                mColor = "#9191ea";
                break;
            case R.id.dsc_grape:
                mColor = "#b01376";
                break;
            case R.id.dsc_flamingo:
                mColor = "#fb9475";
                break;
            case R.id.dsc_graphite:
                mColor = "#3f4243";
                break;
        }
        mOnSelectColorListener.onSelectColor(mColor);
        dismiss();
    }

    public interface OnSelectColorListener {
        void onSelectColor(String color);
    }

}
