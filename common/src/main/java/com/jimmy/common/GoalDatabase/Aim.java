package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.jimmy.common.CalendarSystemDatabase.Schedule;
import com.jimmy.common.ItemWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aim implements Serializable, ItemWrapper {
    public static int VIEW_TYPE = 1;
    @PrimaryKey(autoGenerate = true)
    int id;
    int doneschedules;
    int inprogress;
    String name;
    String color;
    String description;
    boolean done;
    private List<ItemWrapper> items;
    //private List<GoalList> lists;
    private List<Integer> note_ids;
    private List<Integer> schedule_ids;

    @Ignore
    public Aim(){ }

    @Ignore
    public Aim(String name, boolean done,String description, String color, List<ItemWrapper> itemWrapper){
        this.name = name;
        this.doneschedules = 0;
        this.inprogress = 0;
        this.done = done;
        this.description = description;
        this.color = color;
        this.items = itemWrapper;
        //this.lists = goalLists;
        initArrayLists();
    }

    public Aim(int id, String name, boolean done,String description,String color, List<ItemWrapper> itemWrapper) {
        this.id = id;
        this.name = name;
        this.doneschedules = 0;
        this.inprogress = 0;
        this.done = done;
        this.description = description;
        this.color = color;
        this.items = itemWrapper;
        //this.lists = goalLists;
        initArrayLists();
    }

    @Override
    public int getViewType(){
        return VIEW_TYPE;
    }

    /*
    public void deleteListById(int id){
        Log.wtf("deleteListbyId", "delete");
    }

    public void addList(GoalList goalList){
        lists.add(goalList);
    }
*/
    public void deleteById(int id, int view_type){
        switch (view_type){
            case 2:
                deleteSchedule(id);
                break;
            case 3:
                deleteTask(id);
                break;
        }
    }

    public void addTask(Task note){
        if(note != null){
            note_ids.add(items.size());
            items.add(note);
        }
    }

    public void addSchedule(Schedule schedule){
        if(schedule != null){
            schedule_ids.add(items.size());
            items.add(schedule);
            inprogress++;
        }
    }

    public void deleteTask(int id){
        for(int i = 0; i < note_ids.size(); ++i){
            if(note_ids.get(i) == id){
                int position = note_ids.get(i);
                note_ids.remove(i);
                items.remove(position);
                recalculateIds(position);
                break;
            }
        }
    }

    public void deleteSchedule(int id){
        for(int i = 0; i < schedule_ids.size(); ++i){
            if(schedule_ids.get(i) == id){
                int position = schedule_ids.get(i);
                schedule_ids.remove(i);
                items.remove(position);
                recalculateIds(position);
                break;
            }
        }
    }

    public void recalculateIds(int position){
        for(int i = position; i < items.size(); ++i){
            items.get(i).setId(i);
        }
        for(int i = 0; i < note_ids.size(); ++i){
            if(note_ids.get(i) > position){
                note_ids.set(i, note_ids.get(i) - 1);
            }
        }
        for(int i = 0; i < schedule_ids.size(); ++i){
            if(schedule_ids.get(i) > position){
                schedule_ids.set(i, schedule_ids.get(i) - 1);
            }
        }
    }


    public void changeTask(int id, String title, String text, long time){
        /*
        Task mTask = (Task) items.get(id);
        deleteTask(id);
        mTask.changeTask(title, text, time);
        addTask(mTask);
        */
    }

    private void initArrayLists(){
        note_ids  = new ArrayList<>();
        schedule_ids = new ArrayList<>();
    }

    /*
    public List<GoalList> getLists() {
        return lists;
    }

    public void setLists(List<GoalList> lists) {
        this.lists = lists;
    }
    */

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

    public List<ItemWrapper> getItems() {
        return items;
    }

    public void setItems(List<ItemWrapper> items) {
        this.items = items;
    }

    public List<Integer> getTask_ids() {
        return note_ids;
    }

    public void setTask_ids(List<Integer> note_ids) {
        this.note_ids = note_ids;
    }

    public List<Integer> getSchedule_ids() {
        return schedule_ids;
    }

    public void setSchedule_ids(List<Integer> schedule_ids) {
        this.schedule_ids = schedule_ids;
    }
    public void changeTask(String k, String p, long l){
        ;
    }

}
