package com.jimmy.common.GoalDatabase;

import android.arch.persistence.room.TypeConverter;
import android.content.ClipData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.ItemWrapper;

import java.lang.reflect.Type;
import java.util.List;

public class ItemWrapperConverter {
    @TypeConverter
    public String fromItemWrapper(List<ItemWrapper> itemWrappers) {
        if (itemWrappers == null) {
            return (null);
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ItemWrapper.class, new InterfaceAdapter());
        Gson gson = builder.create();
        Type type = new TypeToken<List<ItemWrapper>>() {
        }.getType();
        String json = gson.toJson(itemWrappers, type);
        return json;
    }

    @TypeConverter
    public List<ItemWrapper> toItemWrapper(String ItemWrapperString) {
        if (ItemWrapperString == null) {
            return (null);
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ItemWrapper.class, new InterfaceAdapter());
        Gson gson = builder.create();
        Type type = new TypeToken<List<ItemWrapper>>() {
        }.getType();
        List<ItemWrapper> itemWrappers = gson.fromJson(ItemWrapperString, type);
        return itemWrappers;
    }
}