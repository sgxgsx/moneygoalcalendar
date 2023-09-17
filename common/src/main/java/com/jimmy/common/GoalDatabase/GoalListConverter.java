package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.ItemWrapper;

import java.lang.reflect.Type;
import java.util.List;

public class GoalListConverter {
    @TypeConverter
    public String fromGoalListList(List<GoalList> GoalListList) {
        if (GoalListList == null) {
            return (null);
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ItemWrapper.class, new InterfaceAdapter());
        Gson gson = builder.create();
        Type type = new TypeToken<List<GoalList>>() {
        }.getType();
        String json = gson.toJson(GoalListList, type);
        return json;
    }

    @TypeConverter
    public List<GoalList> toGoalListList(String GoalListString) {
        if (GoalListString == null) {
            return (null);
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ItemWrapper.class, new InterfaceAdapter());
        Gson gson = builder.create();
        Type type = new TypeToken<List<GoalList>>() {
        }.getType();
        List<GoalList> GoalListList = gson.fromJson(GoalListString, type);
        return GoalListList;
    }
}
