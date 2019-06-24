package com.baiwen.business.model;

import lombok.Data;

@Data
public class DayDrop {
    private int id;
    private int userId;
    private String dayStr;
    private int amCount;
    private int pmCount;
}
