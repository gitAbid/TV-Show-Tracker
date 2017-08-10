package com.marblelab.projectshowtracker.Features;

import com.marblelab.projectshowtracker.Data.Shows.Shows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abid Hasan on 8/1/2017.
 */

public class Feature {

    public static List<Shows> SHOWS_LIST=new ArrayList<>();
    public static Feature instance=null;

    public Feature() {
    }
    public static Feature getInstance(){
        if (instance==null){
            instance=new Feature();
        }
        return instance;
    }
}
