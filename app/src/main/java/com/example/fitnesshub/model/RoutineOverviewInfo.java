package com.example.fitnesshub.model;

import com.google.gson.annotations.SerializedName;

public class RoutineOverviewInfo {

    @SerializedName("name")
    private String title;

    private String detail;

    @SerializedName("dateCreated")
    private Integer creationDate;

    @SerializedName("averageRating")
    private Integer rating;

    private String diffilcuty;

    @SerializedName("creator")
    private RoutineCreator author;

    private RoutineCategory category;

    private String image;

    public RoutineOverviewInfo(String title, String detail, Integer creationDate, Integer rating, String diffilcuty, RoutineCreator author, RoutineCategory category) {
        this.title = title;
        this.detail = detail;
        this.creationDate = creationDate;
        this.rating = rating;
        this.diffilcuty = diffilcuty;
        this.author = author;
        this.category = category;
        this.image = null;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public Integer getCreationDate() {
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
        private Integer dateCreated;
        private Integer id;
        private Integer dateLastActive;


        public RoutineCreator(String username, String gender, String avatarUrl, Integer dateCreated, Integer id, Integer dateLastActive) {
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

        public Integer getDateCreated() {
            return dateCreated;
        }

        public Integer getid() {
            return id;
        }

        public Integer getDateLastActive() {
            return dateLastActive;
        }
    }
}