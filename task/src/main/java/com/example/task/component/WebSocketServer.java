package com.example.task.component;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by liq on 2019/5/7.
 */


@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个客户端对应的ProductWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    // 与客户端的连接回话，用于通信
    private Session session;
    // 日志记录
    private Logger log = LoggerFactory.getLogger(WebSocketServer.class);


    /**
     *  连接成功调用的方法
     * @param session 用户的Session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     // 加入set中
        addOnlineCount();           // 在线数加1
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        sendMessage("连接成功");

    }

    /**
     *  连接关闭的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  // 从set中删除
        subOnlineCount();           // 在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后的调用方法
     * @param message   客户端发送的消息
     * @param session   客户端的标识
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(session + ":" + message);
        log.info(message);
    }

    /**
     * 发生错误调用的方法
     * @param session 客户标识
     * @param error 错误信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket出现错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     * @param message  服务器发送消息
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            log.info("推送消息成功，消息为：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }


    /**
     * 群发消息
     * @param message 消息
     * @throws IOException 异常信息
     */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketServer webSocketServer : webSocketSet) {
            webSocketServer.sendMessage(message);
        }
    }


    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }






}
