package com.example.fitnesshub.model;

public class RoutineOverviewInfo {

    private String title, author, imageUrl;
    private Integer reps, time, rating;

    public RoutineOverviewInfo(String title, String author, String imageUrl, Integer reps, Integer time, Integer rating) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.reps = reps;
        this.time = time;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getReps() {
        return reps;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getRating() {
        return rating;
    }
}
