package com.jeek.calendar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jeek.calendar.R;

public class MoneyActivity extends AppCompatActivity {
    TextView tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvMoney.setVisibility(View.VISIBLE);
    }
}