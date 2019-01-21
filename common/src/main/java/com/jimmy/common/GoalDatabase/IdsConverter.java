package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IdsConverter {
    @TypeConverter
    public String fromIdsList(List<Integer> IdsList) {
        if (IdsList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        String json = gson.toJson(IdsList, type);
        return json;
    }

    @TypeConverter
    public List<Integer> toIdsList(String IdsString) {
        if (IdsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> IdsList = gson.fromJson(IdsString, type);
        return IdsList;
    }
}
