package com.exam.controller.websocket.server;
import com.google.gson.Gson;
import com.exam.common.Utils.DateUtil;
import com.exam.dao.UserDao;
import com.exam.pojo.model.UserModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint(value = "/websocket/{user}")
@Component
public class WebsocketServer {


    private static UserDao userDao ;
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebsocketServer.applicationContext = applicationContext;
    }





    // 通过类似GET请求方式传递参数的方法（服务端采用第二种方法"WebSocketHandler"实现）
//    websocket = new WebSocket("ws://127.0.0.1:18080/testWebsocket?id=23&name=Lebron");
    /**
     * 在线人数
     */
    public static AtomicInteger onlineNumber = new AtomicInteger(0);

    /**
     * 所有的对象，每次连接建立，都会将我们自己定义的MyWebSocket存放到List中，
     */
    public static List<WebsocketServer> webSockets = new CopyOnWriteArrayList<WebsocketServer>();

    /**
     * 会话，与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 每个会话的用户
     */
    private String user;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("user") String user) {
        if (user == null || "".equals(user)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        onlineNumber.incrementAndGet();
        for (WebsocketServer websocketServer : webSockets) {
            if (user.equals(websocketServer.user)) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
        this.session = session;
        this.user = user;
        webSockets.add(this);
        System.out.println("有新连接加入！ 当前在线人数" + onlineNumber.get());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber.decrementAndGet();
        webSockets.remove(this);
        System.out.println("有连接关闭！ 当前在线人数" + onlineNumber.get());
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("user") String user) {
        userDao = applicationContext.getBean(UserDao.class);
        UserModel userModel = userDao.selectByUserName(user);
        System.out.println("来自" + user + "消息：" + message);
        UserInfoModel userInfoModel = new UserInfoModel(userModel.getNickName(), userModel.getUserName(), message, DateUtil.getCurrentTimeMillis(), userModel.getAvatar(),onlineNumber);
        String msgJson = new Gson().toJson(userInfoModel);
        pushMessage(user, msgJson, null);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息推送
     *
     * @param message
     * @param uuid    uuid为空则推送全部人员
     */
    public static void pushMessage(String user, String message, String uuid) {
        if (uuid == null || "".equals(uuid)) {
            for (WebsocketServer websocketServer : webSockets) {
                websocketServer.sendMessage(message);
            }
        } else {
            for (WebsocketServer websocketServer : webSockets) {
                if (uuid.equals(websocketServer.user)) {
                    websocketServer.sendMessage(message);
                }
            }
        }

    }
}
