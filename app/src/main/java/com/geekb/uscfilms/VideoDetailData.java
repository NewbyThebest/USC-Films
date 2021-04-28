package com.geekb.uscfilms;

import java.util.List;

public class VideoDetailData {
    public String key;
    public String id;
    public String trailer;
    public String poster_path;
    public String title;
    public List<GenresData> genres;
    public String release_date;
    public String overview;


    public VideoDetailData() {

    }

    public class GenresData{
        String id;
        String name;
    }

}