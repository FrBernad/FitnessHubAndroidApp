package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineData {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("name")
    private String title;

    @Expose
    @SerializedName("detail")
    private String detail;

    @Expose
    @SerializedName("dateCreated")
    private String creationDate;

    @Expose
    @SerializedName("averageRating")
    private Integer rating;

    @Expose
    @SerializedName("diffilcuty")
    private String diffilcuty;

    @Expose
    @SerializedName("creator")
    private RoutineCreator author;

    @Expose
    @SerializedName("category")
    private RoutineCategory category;

    private String image;

    public RoutineData(Integer id, String title, String detail, String creationDate, Integer rating, String diffilcuty, RoutineCreator author, RoutineCategory category, String image) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.creationDate = creationDate;
        this.rating = rating;
        this.diffilcuty = diffilcuty;
        this.author = author;
        this.category = category;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Integer getRating() {
        return rating;
    }

    public String getDiffilcuty() {
        return diffilcuty;
    }

    public RoutineCreator getAuthor() {
        return author;
    }

    public RoutineCategory getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public static class RoutineCategory {
        private Integer id;
        private String name;
        private String detail;

        public RoutineCategory(Integer id, String name, String detail) {
            this.id = id;
            this.name = name;
            this.detail = detail;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDetail() {
            return detail;
        }
    }

    public static class RoutineCreator {
        private String username;
        private String gender;
        private String avatarUrl;
        private String dateCreated;
        private Integer id;
        private String dateLastActive;


        public RoutineCreator(String username, String gender, String avatarUrl, String dateCreated, Integer id, String dateLastActive) {
            this.username = username;
            this.gender = gender;
            this.avatarUrl = avatarUrl;
            this.dateCreated = dateCreated;
            this.id = id;
            this.dateLastActive = dateLastActive;
        }

        public String getUsername() {
            return username;
        }

        public String getGender() {
            return gender;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public Integer getid() {
            return id;
        }

        public String getDateLastActive() {
            return dateLastActive;
        }
    }
}