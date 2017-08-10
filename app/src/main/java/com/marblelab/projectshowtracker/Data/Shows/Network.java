package com.marblelab.projectshowtracker.Data.Shows;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abid Hasan on 8/3/2017.
 */

public class Network{
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("country")
    Country country;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
