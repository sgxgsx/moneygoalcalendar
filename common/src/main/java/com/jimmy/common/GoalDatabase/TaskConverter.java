package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TaskConverter {
    @TypeConverter
    public String fromTaskList(List<Task> TaskList) {
        if (TaskList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Task>>() {
        }.getType();
        String json = gson.toJson(TaskList, type);
        return json;
    }

    @TypeConverter
    public List<Task> toTaskList(String TaskString) {
        if (TaskString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> TaskList = gson.fromJson(TaskString, type);
        return TaskList;
    }
}