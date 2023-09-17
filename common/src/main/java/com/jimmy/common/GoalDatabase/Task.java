package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.jimmy.common.ItemWrapper;

import java.io.Serializable;

@Entity
public class Task implements Serializable, ItemWrapper {
    public static int VIEW_TYPE = 3;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String text;
    private long time;

    @Ignore
    public Task(){

    }

    @Ignore
    public Task(String title, String text, long time) {
        this.title = title;
        this.text = text;
        this.time = time;
    }

    public Task(int id, String title, String text, long time) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.time = time;
    }

    @Override
    public int getViewType(){
        return VIEW_TYPE;
    }

    public void changeTask(String title, String text, long time){
        setText(text);
        setTitle(title);
        setTime(time);
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String n){
        ;
    }
    public void setDescription(String d){
        ;
    }

    public void setColor(String c){
        ;
    }

    public void changeTask(int i,String k, String p, long l){
        ;
    }

    public void deleteTask(int i){
        ;
    }
    public void addTask(Task n){
        ;
    }
}
