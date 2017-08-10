package com.marblelab.projectshowtracker.Data.Shows;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abid Hasan on 8/3/2017.
 */

public class Image {
    @SerializedName("medium")
    public String medium="";
    @SerializedName("original")
    public String original="";

    public String getMedium() {
        if (medium==null){
            return "https://www.rockinghamvisitorcentre.com.au/images/no-image.jpg";
        }
        return medium;
    }

    public String getOriginal() {
        if (original==null){
            return "https://www.rockinghamvisitorcentre.com.au/images/no-image.jpg";
        }
        return original;
    }
}
