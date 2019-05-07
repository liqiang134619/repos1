package com.luopan.barrage.websocket;

import com.luopan.barrage.modal.Barrage;
import com.luopan.barrage.service.IBarrageService;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ServerEndpoint(value = "/barrage")
public class WebSocket {

  private Session session;

  // 记录当前连接数
  private static AtomicLong count = new AtomicLong();

  // 存放客户端对应Session
  private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

  private static IBarrageService barrageService;

  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    webSocketSet.add(this);
    log.info("当前连接数：{}", count.incrementAndGet());
  }

  @OnClose
  public void onClose() {
    webSocketSet.remove(this);
    log.info("当前连接数：{}", count.decrementAndGet());
  }

  @OnMessage
  public void onMessage(String message) {
    log.info("收到客户端消息：{}", message);
    Barrage barrage = new Barrage().setMessage(message);
    barrageService.insert(barrage);
  }

  public void sendMessage(List<Barrage> list) {
    list.forEach(barrage -> {
      String message = barrage.getMessage();
      webSocketSet.forEach(
          webSocket -> {
            try {
              webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
      );
    });
  }

  public static void setPropertyByApplicationContext(ApplicationContext applicationContext) {
    barrageService = (IBarrageService) applicationContext.getBean("barrageService");
  }

}
