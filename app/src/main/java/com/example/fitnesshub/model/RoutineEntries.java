package com.example.fitnesshub.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RoutineEntries {

    private Integer totalCount;

    private String orderBy;

    private String direction;

    @SerializedName("results")
    private ArrayList<RoutineOverviewInfo> entries;

    private Integer size;

    private Integer page;

    private Boolean isLastPage;

    public RoutineEntries(Integer totalCount, String orderBy, String direction, ArrayList<RoutineOverviewInfo> entries, Integer size, Integer page, Boolean isLastPage) {
        this.totalCount = totalCount;
        this.orderBy = orderBy;
        this.direction = direction;
        this.entries = entries;
        this.size = size;
        this.page = page;
        this.isLastPage = isLastPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public ArrayList<RoutineOverviewInfo> getEntries() {
        return entries;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }

    public Boolean getLastPage() {
        return isLastPage;
    }
}
