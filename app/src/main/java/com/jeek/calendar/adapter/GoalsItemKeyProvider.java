package com.jeek.calendar.adapter;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jimmy.common.GoalDatabase.Goal;

import java.util.List;

import androidx.recyclerview.selection.ItemKeyProvider;


// TODO http://androidkt.com/recyclerview-selection-28-0-0/


public class GoalsItemKeyProvider extends ItemKeyProvider {
    private final List<Goal> itemList;

    public GoalsItemKeyProvider(int scope, List<Goal> itemList) {
        super(scope);
        this.itemList = itemList;
    }

    @Nullable
    @Override
    public Object getKey(int position) {
        return itemList.get(position);
    }

    @Override
    public int getPosition(@NonNull Object key) {
        return itemList.indexOf(key);
    }
}