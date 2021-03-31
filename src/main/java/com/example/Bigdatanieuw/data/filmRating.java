package com.example.Bigdatanieuw.data;

public class filmRating {
    public String title;
    public long tconst;
    public long minutes;
    public float rating;

    public filmRating(String title, long tconst, long minutes, float rating){
        this.title = title;
        this.tconst = tconst;
        this.minutes = minutes;
        this.rating = rating;
    }
}
