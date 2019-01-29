package com.jimmy.common.GoalDatabase;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "GoalsTable")
public class  Goal implements Serializable{
    public static int VIEW_TYPE = 0;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int doneschedules;
    private int inprogress;
    private boolean done;
    private boolean mode = false;
    private String goal_name;
    private String description;
    private String image_path;
    private long date_to;
    //private List<ItemWrapper> items;
    private List<GoalList> lists;
    //private List<Integer> aim_ids;
    //private List<Integer> note_ids;
    //private List<Integer> schedule_ids;

    @Ignore
    public Goal(){

    }

    @Ignore
    public Goal(String goal_name, long date_to, String description, String image_path) {
        this.goal_name = goal_name;
        this.date_to = date_to;
        this.description = description;
        this.image_path = image_path;
        this.done = true;
        this.mode = false;
        lists = new ArrayList<>();
        //aim_ids = new ArrayList<>();
        //note_ids = new ArrayList<>();
        //schedule_ids = new ArrayList<>();
    }

    public Goal(int id, String goal_name, long date_to, String description, String image_path) {
        this.id = id;
        this.goal_name = goal_name;
        this.date_to = date_to;
        this.description = description;
        this.inprogress = 0;
        this.doneschedules = 0;
        this.image_path = image_path;
        this.done = true;
        lists = new ArrayList<>();
        /*
        aim_ids = new ArrayList<>();
        note_ids = new ArrayList<>();
        schedule_ids = new ArrayList<>();
        */
    }

    public int getViewType(){
        return VIEW_TYPE;
    }

    public void addList(GoalList goalList){
        if(lists != null && goalList != null){
            lists.add(goalList);
        }
    }

    public void deleteList(int id){
        if(lists.size() - 1 > id){
            lists.remove(id);
        }
    }

    public List<GoalList> getLists() {
        return lists;
    }

    public void setLists(List<GoalList> lists) {
        this.lists = lists;
    }

    /*
        public void addAim(Aim aim){
            if(aim != null){
                aim_ids.add(items.size());
                items.add(aim);
                inprogress++;
            }
        }

        public void deleteById(int id, int view_type){
            switch (view_type){
                case 1:
                    deleteAim(id);
                    break;
                case 2:
                    deleteSchedule(id);
                    break;
                case 3:
                    deleteNote(id);
                    break;
            }
        }

        public void addNote(Note note){
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

        public void deleteAim(int id){
            for(int i = 0; i < aim_ids.size(); ++i){
                if(aim_ids.get(i) == id){
                    int position = aim_ids.get(i);
                    aim_ids.remove(i);
                    items.remove(position);
                    recalculateIds(position);
                    break;
                }
            }
        }

        public void deleteNote(int id){
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
            for(int i = 0; i < aim_ids.size(); ++i){
                if(aim_ids.get(i) > position){
                    aim_ids.set(i, aim_ids.get(i) - 1);
                }
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

        public void reasign(){
            for(int i = 0; i < items.size(); ++i){
                items.get(i).setId(i);
                switch (items.get(i).getViewType()){
                    case 1:
                        aim_ids.add(i);
                        break;
                    case 2:
                        schedule_ids.add(i);
                        break;
                    case 3:
                        note_ids.add(i);
                        break;
                }
            }
        }
    */
    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public static void setViewType(int viewType) {
        VIEW_TYPE = viewType;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
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

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public long getDate_to() {
        return date_to;
    }

    public void setDate_to(long date_to) {
        this.date_to = date_to;
    }
/*
    public List<ItemWrapper> getItems() {
        return items;
    }

    public void setItems(List<ItemWrapper> items) {
        this.items = items;
        reasign();
    }

    public List<GoalList> getLists() {
        return lists;
    }

    public void setLists(List<GoalList> lists) {
        this.lists = lists;
    }


    public List<Integer> getAim_ids() {
        return aim_ids;
    }

    public void setAim_ids(List<Integer> aim_ids) {
        this.aim_ids = aim_ids;
    }

    public List<Integer> getNote_ids() {
        return note_ids;
    }

    public void setNote_ids(List<Integer> note_ids) {
        this.note_ids = note_ids;
    }

    public List<Integer> getSchedule_ids() {
        return schedule_ids;
    }

    public void setSchedule_ids(List<Integer> schedule_ids) {
        this.schedule_ids = schedule_ids;
    }
*/
    public void setName(String s){
        ;
    }

    public void setColor(String c){
        ;
    }

    public void changeNote(String k, String p, long l){
        ;
    }

    public void changeNote(int i,String k, String p, long l){
        ;
    }
}

