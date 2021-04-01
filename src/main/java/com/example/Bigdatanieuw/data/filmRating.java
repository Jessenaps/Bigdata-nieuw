package com.example.Bigdatanieuw.data;

public class filmRating {
    public String title;
    public long tconst;
    public long minutes;
    public String type;
    public double rating;

    public filmRating(String title, long tconst, long minutes, String type, double rating){
        this.title = title;
        this.tconst = tconst;
        this.minutes = minutes;
        this.type = type;
        this.rating = rating;
    }
}
