package com.marblelab.projectshowtracker.Data.Shows;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abid Hasan on 8/3/2017.
 */

public class Schedule{
    @SerializedName("time")
    String time;
    @SerializedName("days")
    List<String> days;

    public String getTime() {
        return time;
    }

    public List<String> getDays() {
        return days;
    }
}