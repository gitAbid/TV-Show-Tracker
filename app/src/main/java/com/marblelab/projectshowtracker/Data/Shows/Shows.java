package com.marblelab.projectshowtracker.Data.Shows;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abid Hasan on 7/29/2017.
 */

public class Shows {

  /*  @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String url;*/
    @SerializedName("name")
    private String name;
/*    @SerializedName("type")
    private String type;
    @SerializedName("language")
    private String language;*/
    @SerializedName("genres")
    private List<String> genres;
    /*
    @SerializedName("status")
    private String status;
    @SerializedName("runtime")
    private int runtime;*/
    @SerializedName("premiered")
    private String premiered;/*
    @SerializedName("officialSite")
    private String officialSite;*/
    @SerializedName("schedule")
    private Schedule schedule;
    @SerializedName("rating")
    private Rating rating;
   @SerializedName("weight")
    private int weight;
    @SerializedName("network")
    private Network network;
   /* @SerializedName("webChannel")
    private String webChannel;
    //private External external;*/
    @SerializedName("image")
    private Image image;
    @SerializedName("summary")
    private String summary;
/*    @SerializedName("updated")
    private int updated;*/

    // private Links _links;
    //private Previousepisode previousepisode;

    public String getName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }

    public Image getImage() {
        return image;
    }

/*
    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getLanguage() {
        return language;
    }*/

    public List<String> getGenres() {
        return genres;
    }
/*
    public String getStatus() {
        return status;
    }

    public int getRuntime() {
        return runtime;
    }*/

    public String getPremiered() {
        return premiered;
    }/*

    public String getOfficialSite() {
        return officialSite;
    }
*/
    public Schedule getSchedule() {
        return schedule;
    }

    public int getWeight() {
        return weight;
    }

    public Network getNetwork() {
        return network;
    }
/*
    public String getWebChannel() {
        return webChannel;
    }
*/
    public String getSummary() {
        return summary;
    }
/*
    public int getUpdated() {
        return updated;
    }
*/






}