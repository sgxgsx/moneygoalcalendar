package com.jimmy.common;

import com.jimmy.common.GoalDatabase.Task;

public interface ItemWrapper{
    int getViewType();
    int getId();
    void setId(int id);

    // делать их пустыми для всех кроме тех кому они нужны
    void setName(String n);
    void setDescription(String d);
    void setColor(String c);
    void changeTask(int i, String k, String p, long l);
    void changeTask(String k, String p, long l);
    void addTask(Task n);
    void deleteTask(int i);
}
