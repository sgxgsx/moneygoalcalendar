package com.jimmy.common.bean;

import java.io.Serializable;
import java.util.List;

public class CalendarClass implements Serializable{
    private int[] allowedAttendeeTypes;
    private String accountName;
    private String displayName;
    private String location;           // ????????
    private String timeZone;
    private int id;
    private String name;
    private int color;
    private int access_level;
    private String owner_account;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAccess_level() {
        return access_level;
    }

    public void setAccess_level(int access_level) {
        this.access_level = access_level;
    }

    public String getOwner_account() {
        return owner_account;
    }

    public void setOwner_account(String owner_account) {
        this.owner_account = owner_account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getAllowedAttendeeTypes() {
        return allowedAttendeeTypes;
    }

    public void setAllowedAttendeeTypes(int[] allowedAttendeeTypes) {
        this.allowedAttendeeTypes = allowedAttendeeTypes;
    }
    public void setAllowedAttendeeTypes(String allowed){
        String[] allowedA = allowed.split(",");
        allowedAttendeeTypes = new int[allowedA.length];
        for(int i =0; i< allowedA.length; ++i){
            allowedAttendeeTypes[i] = Integer.parseInt(allowedA[i]);
        }
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
