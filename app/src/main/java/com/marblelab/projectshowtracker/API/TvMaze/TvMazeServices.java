package com.marblelab.projectshowtracker.API.TvMaze;

import com.marblelab.projectshowtracker.Data.Shows.Shows;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abid Hasan on 7/29/2017.
 */

public interface TvMazeServices {
    @GET("shows")
    Call<List<Shows>> getShows();

    @GET("shows")
    Call<List<Shows>> updateShowsList(@Query("page") int page);
}


