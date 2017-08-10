package com.marblelab.projectshowtracker.API.TvMaze;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abid Hasan on 7/29/2017.
 */

public class TvMazeApiClient {

    public static final String BASE_URL="http://api.tvmaze.com/";
    public static Retrofit instance=null;

    public static Retrofit getInstance(){
        if (instance==null){
            instance=new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

}
