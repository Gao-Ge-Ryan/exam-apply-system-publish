package com.exam.controller.websocket.server;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class UserInfoModel {
    private String nickName;
    private String userAccount;
    private String message;
    private Long currentTime;
    private String headPortrait;
    private AtomicInteger onLineNum;

    public UserInfoModel(String nickName, String userAccount, String message, Long currentTime, String headPortrait, AtomicInteger onLineNum) {
        this.nickName = nickName;
        this.userAccount = userAccount;
        this.message = message;
        this.currentTime = currentTime;
        this.headPortrait = headPortrait;
        this.onLineNum = onLineNum;
    }

    public UserInfoModel(String nickName, String userAccount, String message, Long currentTime, String headPortrait) {
        this.nickName = nickName;
        this.userAccount = userAccount;
        this.message = message;
        this.currentTime = currentTime;
        this.headPortrait = headPortrait;
    }

    public UserInfoModel(String nickName, String userAccount, String message, Long currentTime) {
        this.nickName = nickName;
        this.userAccount = userAccount;
        this.message = message;
        this.currentTime = currentTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }
}
