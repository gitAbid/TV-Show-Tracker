package com.marblelab.projectshowtracker.Data.Shows;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abid Hasan on 8/3/2017.
 */

public class Rating {
    @SerializedName("average")
    float average;

    public float getAverage() {
        return average;
    }
}
