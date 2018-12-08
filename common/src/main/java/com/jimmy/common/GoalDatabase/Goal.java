package com.jimmy.common.GoalDatabase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import com.jimmy.common.CalendarSystemDatabase.Schedule;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "GoalsTable")
public class  Goal implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int doneschedules;
    private int inprogress;
    private String goal_name;
    private String description;
    private long date_to;
    private List<Aim> aims;
    private List<GoalSchedule> schedules;


    @Ignore
    public Goal(String goal_name, long date_to,String description, List<Aim> aims, List<GoalSchedule> schedules) {
        this.goal_name = goal_name;
        this.date_to = date_to;
        this.aims = aims;
        this.schedules = schedules;
        this.description = description;
    }

    public Goal(int id, String goal_name, long date_to,String description, List<Aim> aims, List<GoalSchedule> schedules) {
        this.id = id;
        this.goal_name = goal_name;
        this.date_to = date_to;
        this.aims = aims;
        this.description = description;
        this.schedules = schedules;
        this.inprogress = 0;
        this.doneschedules = 0;
    }

    public void addAim(Aim aim){
        if(aim != null){
            aims.add(aim);
            inprogress++;
        }
    }

    public void deleteAimById(int id){
        for(int i=0; i < aims.size(); ++i){
            if(aims.get(i).getId() == id){
                Log.wtf("Goal", "delete aim " + String.valueOf(id) + " i " + String.valueOf(i) + " i id " + String.valueOf(aims.get(i).getId()));
                if(aims.get(i).isDone()){
                    //TODO ЕСЛИ УДАЛЯЕТСЯ AIM ПРОВЕРЯТЬ КОЛ-ВО SHEDULE В НЕМ И УБИВАТЬ НЕНУЖНОЕ КОЛ-ВО
                    doneschedules--;
                } else{
                    inprogress--;
                }
                aims.remove(i);
                for(int j=i; j< aims.size(); ++j){
                    aims.get(j).setId(j);
                }
                break;
            }
        }
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

    public List<Aim> getAims() {
        return aims;
    }

    public void setAims(List<Aim> aims) {
        this.aims = aims;
    }

    public List<GoalSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<GoalSchedule> schedules) {
        this.schedules = schedules;
    }
}
