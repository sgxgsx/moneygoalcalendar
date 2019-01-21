package com.jeek.calendar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeek.calendar.R;

public class AddonsFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // The request codes
    private static final int GO_BACK_CALL_BACK = 1;


    private String mParam1;
    private String mParam2;
    private TextView textView;
    private LinearLayout llGoBack;

    private OnFragmentInteractionListener mListener;

    public AddonsFragment() { }

    public static AddonsFragment newInstance(String param1, String param2) {
        AddonsFragment fragment = new AddonsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addons, container, false);

        llGoBack = view.findViewById(R.id.llGoBack);
        llGoBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        Log.wtf("click", "sec");
        switch (v.getId()){
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

    public void onAction(int callback){
        if (mListener != null) {
            mListener.onFragmentInteraction(callback);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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