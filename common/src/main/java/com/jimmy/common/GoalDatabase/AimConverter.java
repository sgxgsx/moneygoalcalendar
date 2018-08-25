package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AimConverter {
    @TypeConverter
    public String fromAimList(List<Aim> AimList) {
        if (AimList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Aim>>() {
        }.getType();
        String json = gson.toJson(AimList, type);
        return json;
    }

    @TypeConverter
    public List<Aim> toAimList(String AimString) {
        if (AimString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Aim>>() {
        }.getType();
        List<Aim> AimList = gson.fromJson(AimString, type);
        return AimList;
    }
}
