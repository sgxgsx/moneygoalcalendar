package com.jeek.calendar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;
import com.jimmy.common.GoalDatabase.Goal;

public class GoalSettingsFragment extends Fragment implements View.OnClickListener {
    // The request codes
    private static final int GO_BACK_CALL_BACK = 1;
    private static final int CHECKED_CALL_BACK = 2;
    private static final int UNCHECKED_CALL_BACK = 3;

    private String mParam1;
    private String mParam2;
    private TextView textView;
    private LinearLayout llGoBack;
    private CheckBox cbMode;
    private Goal mGoal;

    private OnGoalSettingsFragmentListner mListener;

    public GoalSettingsFragment() {
    }

    public static GoalSettingsFragment newInstance(Goal goal) {
        GoalSettingsFragment fragment = new GoalSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mGoal != null) {
            Log.wtf("name", mGoal.getGoal_name());
        } else {
            Log.wtf("shit", "shit");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal_settings, container, false);

        llGoBack = view.findViewById(R.id.llGoBack);
        cbMode = view.findViewById(R.id.cbMode);
        cbMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.wtf("check", "checked");
                if (cbMode.isChecked()) onAction(CHECKED_CALL_BACK);
                else onAction(UNCHECKED_CALL_BACK);
            }
        });
        llGoBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.wtf("click", "sec");
        switch (v.getId()) {
            case R.id.llGoBack:
                Log.wtf("onclick", "click");
                onAction(GO_BACK_CALL_BACK);
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void onAction(int callback) {
        if (mListener != null) {
            mListener.onGoalSettingsFragmentListner(callback);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGoalSettingsFragmentListner) {
            mListener = (OnGoalSettingsFragmentListner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}