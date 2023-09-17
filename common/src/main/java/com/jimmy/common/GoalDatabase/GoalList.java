package com.jimmy.common.GoalDatabase;

import android.util.Log;

import com.jimmy.common.ItemWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoalList implements Serializable {
    private String name;
    private int id;
    private List<ItemWrapper> items;

    public GoalList(){

    }

    public GoalList(int id, String name) {
        this.name = name;
        this.id = id;
        this.items = new ArrayList<>();
    }

    public GoalList(int id, String name, List<ItemWrapper> lists) {
        this.name = name;
        this.id = id;
        this.items = lists;
    }

    public void addItem(ItemWrapper itemWrapper){
        items.add(itemWrapper);
        Log.wtf("inside", "added");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemWrapper> getItems() {
        return items;
    }

    public void setItems(List<ItemWrapper> items) {
        this.items = items;
    }
}
