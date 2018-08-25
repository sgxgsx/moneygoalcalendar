package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.file.attribute.GroupPrincipal;
import java.util.List;


public class GoalScheduleConverter {
    @TypeConverter
    public String fromGoalScheduleList(List<GoalSchedule> GoalScheduleList) {
        if (GoalScheduleList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<GoalSchedule>>() {
        }.getType();
        String json = gson.toJson(GoalScheduleList, type);
        return json;
    }

    @TypeConverter
    public List<GoalSchedule> toGoalScheduleList(String GoalScheduleString) {
        if (GoalScheduleString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<GoalSchedule>>() {
        }.getType();
        List<GoalSchedule> GoalScheduleList = gson.fromJson(GoalScheduleString, type);
        return GoalScheduleList;
    }
}
