package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineExecution {
    @Expose
    @SerializedName("duration")
    private Integer duration;

    @Expose
    @SerializedName("wasModified")
    private boolean wasModified;

    public RoutineExecution(Integer duration, boolean wasModified) {
        this.duration = duration;
        this.wasModified = wasModified;
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean isWasModified() {
        return wasModified;
    }
}
