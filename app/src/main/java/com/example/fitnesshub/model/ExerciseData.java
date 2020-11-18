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

    private boolean isRunning;

    public ExerciseData(Integer id, String name, String detail, Integer time, Integer reps) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.time = time;
        this.reps = reps;
        isRunning = false;
    }

    @Override
    public String toString() {
        return "ExerciseData{" +
                "name='" + name + '\'' +
                '}';
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

    public String getTimeString() {
        return "TIME: " + time;
    }

    public String getRepsString() {
        return "REPS: " + reps;
    }

    public void setRunning(boolean state) {
        isRunning = state;
    }

    public boolean isRunning() {
        return isRunning;
    }
}

