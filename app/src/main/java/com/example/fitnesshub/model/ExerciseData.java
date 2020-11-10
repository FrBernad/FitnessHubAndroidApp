package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExerciseData {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("detail")
    private String detail;

    @Expose
    @SerializedName("duration")
    private Integer time;

    @Expose
    @SerializedName("repetitions")
    private Integer reps;

    public ExerciseData(Integer id, String name, String detail, Integer time, Integer reps) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.time = time;
        this.reps = reps;
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

    public Integer getTime() {
        return time;
    }

    public Integer getReps() {
        return reps;
    }
}

