package com.exam.controller.poi.word;

import lombok.Data;

@Data
public class TicketInfoModel {
    private String title;
    private String name;
    private String IDNum;
    private String ticketNum;
    private String roomNum;
    private String pictureURL;
    private Long startTime;
    private Long endTime;
}
