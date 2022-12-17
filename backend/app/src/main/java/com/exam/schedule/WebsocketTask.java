package com.exam.schedule;

import com.google.gson.Gson;
import com.exam.common.Utils.DateUtil;
import com.exam.controller.websocket.server.UserInfoModel;
import com.exam.controller.websocket.server.WebsocketServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class WebsocketTask {

    @Scheduled(cron = "0/20 * * * * ?")
    public void index() {
        UserInfoModel userInfoModel = new UserInfoModel("官方提醒您", "admin", " 请文明聊天,本界面仅用于测试，非内部人员禁止使用，若非法使用，请自行承担法律责任，谢谢您的合作！", DateUtil.getCurrentTimeMillis(), "http://1.116.106.177/14b89bebc97949bf8a0470b9af1decf9.png");
        String msgJson = new Gson().toJson(userInfoModel);
        WebsocketServer.pushMessage("admin",msgJson , null);
    }
}
