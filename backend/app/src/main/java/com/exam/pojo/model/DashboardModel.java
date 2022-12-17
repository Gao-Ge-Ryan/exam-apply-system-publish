package com.exam.pojo.model;

import lombok.Data;

@Data
public class DashboardModel {
    private int value;
    private String name;

    public DashboardModel(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
