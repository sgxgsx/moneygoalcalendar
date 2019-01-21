package com.jimmy.common;

import com.jimmy.common.GoalDatabase.Note;

public interface ItemWrapper{
    int getViewType();
    int getId();
    void setId(int id);

    // делать их пустыми для всех кроме тех кому они нужны
    void setName(String n);
    void setDescription(String d);
    void setColor(String c);
    void changeNote(int i, String k, String p, long l);
    void changeNote(String k, String p, long l);
    void addNote(Note n);
    void deleteNote(int i);
}
