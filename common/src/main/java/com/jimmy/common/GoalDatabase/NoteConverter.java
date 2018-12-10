package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NoteConverter {
    @TypeConverter
    public String fromNoteList(List<Note> NoteList) {
        if (NoteList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Note>>() {
        }.getType();
        String json = gson.toJson(NoteList, type);
        return json;
    }

    @TypeConverter
    public List<Note> toNoteList(String NoteString) {
        if (NoteString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Note>>() {
        }.getType();
        List<Note> NoteList = gson.fromJson(NoteString, type);
        return NoteList;
    }
}