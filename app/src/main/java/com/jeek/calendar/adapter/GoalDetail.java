package com.jeek.calendar.adapter;

import android.content.ClipData;
import android.support.annotation.Nullable;

import com.jimmy.common.GoalDatabase.Goal;

import androidx.recyclerview.selection.ItemDetailsLookup;

public class GoalDetail extends ItemDetailsLookup.ItemDetails {
    private final int adapterPosition;
    private final Goal selectionKey;

    public GoalDetail(int adapterPosition, Goal selectionKey) {
        this.adapterPosition = adapterPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Object getSelectionKey() {
        return selectionKey;
    }
}