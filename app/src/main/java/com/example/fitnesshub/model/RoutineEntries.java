package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoutineEntries<T> {

    @Expose
    private Integer totalCount;
    @SerializedName("orderBy")
    @Expose
    private String orderBy;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("results")
    @Expose
    private List<T> entries;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("isLastPage")
    @Expose
    private Boolean isLastPage;

    public RoutineEntries(Integer totalCount, String orderBy, String direction, ArrayList<T> entries, Integer size, Integer page, Boolean isLastPage) {
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

    public List<T> getEntries() {
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
