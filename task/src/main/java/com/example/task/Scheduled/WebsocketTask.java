package com.example.task.Scheduled;

import com.example.task.component.WebSocketServer;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by liq on 2019/5/7.
 */
@Component
public class WebsocketTask {

    private static Logger logger = LoggerFactory.getLogger(WebsocketTask.class);



    @Scheduled(fixedRate=1000*60*5)//每隔5分钟执行一次，服务器启动就立即执行
//      @Scheduled(cron = "* 1/5 * * * *")  5分钟执行一次，
    public void task1(){
        System.err.println("*********   定时任务执行   **************");
        CopyOnWriteArraySet<WebSocketServer> webSocketSet = WebSocketServer.getWebSocketSet();
        System.out.println(webSocketSet);
        for (WebSocketServer webSocketServer : webSocketSet) {
            webSocketServer.sendMessage("定时发送" + new Date());
        }

        System.err.println("/n 定时任务完成.......");
    }

}
