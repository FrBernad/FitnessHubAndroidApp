package com.example.fitnesshub.model;

public class ExerciseOverviewInfo {

    private String exerciseName;
    private Integer reps, time;

    public ExerciseOverviewInfo(String exerciseName, Integer reps){
        this.exerciseName = exerciseName;
        this.reps = reps;
        this.time =reps;
    }

    public String getExerciseName() {
        return exerciseName;
    }


    public Integer getQuantity() {
        return reps;
    }

    public Integer getTime() {
        return time;
    }
}
