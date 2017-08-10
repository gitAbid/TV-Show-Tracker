package com.marblelab.projectshowtracker.Data.Shows;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abid Hasan on 8/3/2017.
 */

public class Country{
    @SerializedName("name")
    String name;
    @SerializedName("code")
    String code;
    @SerializedName("timezone")
    String timezone;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getTimezone() {
        return timezone;
    }
}
