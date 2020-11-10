package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineCycleData {

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
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("order")
    private Integer order;

    public RoutineCycleData(Integer id, String name, String detail, String type, Integer order) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.order = order;
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

    public String getType() {
        return type;
    }

    public Integer getOrder() {
        return order;
    }
}
