package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.jimmy.common.CalendarSystemDatabase.Schedule;

import java.io.Serializable;
import java.util.List;

@Entity
public class Aim implements Serializable{
    @PrimaryKey(autoGenerate = true)
    int id;
    int doneschedules;
    int inprogress;
    String name;
    String color;
    String description;
    boolean done;
    List<GoalSchedule> scheduleList;
    List<Note> noteList;

    @Ignore
    public Aim(String name, boolean done,String description, String color, List<GoalSchedule> scheduleList, List<Note> noteList){
        this.name = name;
        this.doneschedules = 0;
        this.inprogress = 0;
        this.done = done;
        this.description = description;
        this.scheduleList = scheduleList;
        this.color = color;
        this.noteList = noteList;
    }

    public Aim(int id, String name, boolean done,String description,String color, List<GoalSchedule> scheduleList, List<Note> noteList) {
        this.id = id;
        this.name = name;
        this.doneschedules = 0;
        this.inprogress = 0;
        this.done = done;
        this.description = description;
        this.scheduleList = scheduleList;
        this.color = color;
        this.noteList = noteList;
    }

    public void addNote(Note note){
        noteList.add(note);
    }

    public void deleteNote(int id){
        if(id == noteList.size() - 1){
            noteList.remove(id);
        } else{
            noteList.remove(id);
            for(int i=id; i<noteList.size(); i++){
                noteList.get(i).setId(i);
            }
        }
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDoneschedules() {
        return doneschedules;
    }

    public void setDoneschedules(int doneschedules) {
        this.doneschedules = doneschedules;
    }

    public int getInprogress() {
        return inprogress;
    }

    public void setInprogress(int inprogress) {
        this.inprogress = inprogress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<GoalSchedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<GoalSchedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
