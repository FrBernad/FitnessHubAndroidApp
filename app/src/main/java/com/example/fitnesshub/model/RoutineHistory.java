package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineHistory {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("userId")
    private Integer userId;

    @Expose
    @SerializedName("date")
    private Long date;

    @Expose
    @SerializedName("duration")
    private Integer duration;

    @Expose
    @SerializedName("wasModified")
    private Boolean wasModified;

    @Expose
    @SerializedName("routine")
    private RoutineData routine;

    public RoutineHistory(Integer id, Integer userId, Long date, Integer duration, Boolean wasModified, RoutineData routine) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.duration = duration;
        this.wasModified = wasModified;
        this.routine = routine;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Long getDate() {
        return date;
    }

    public Integer getDuration() {
        return duration;
    }

    public Boolean getWasModified() {
        return wasModified;
    }

    public RoutineData getRoutine() {
        return routine;
    }
}
