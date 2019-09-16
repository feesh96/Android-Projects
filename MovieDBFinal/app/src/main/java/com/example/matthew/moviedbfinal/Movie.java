package com.example.matthew.moviedb;

/**
 * Created by Matthew on 10/14/2017.
 */

public class Movie {

    private String title;
    private String avgVote;
    private String popularity;
    private String overview;
    private String posterPath;
    private boolean isLiked;

    public Movie() {

    }

    public Movie(String title, String avgVote, String popularity,
                 String overview, String posterPath) {
        this.title = title;
        this.avgVote = avgVote;
        this.popularity = popularity;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public void setAvgVote(String avgVote) {
        this.avgVote = avgVote;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setIsLiked(boolean isLiked) { this.isLiked = isLiked; }

    public String getAvgVote() {
        return this.avgVote;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPopularity() {
        return this.popularity;
    }

    public String getOverview() { return this.overview; }

    public String getPosterPath() {
        return this.posterPath;
    }

    public boolean isLiked() { return this.isLiked; }

}
