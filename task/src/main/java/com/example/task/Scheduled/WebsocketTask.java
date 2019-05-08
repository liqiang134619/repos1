package com.example.task.Scheduled;

import com.example.task.component.Excel;
import com.example.task.component.WebSocketServer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by liq on 2019/5/7.
 * 单线程
 */
@Component
public class WebsocketTask {

    private static Logger logger = LoggerFactory.getLogger(WebsocketTask.class);

    private List<String> list = new ArrayList<>();


//    @Scheduled(fixedRate=1000*5)//每隔5分钟执行一次，服务器启动就立即执行
      @Scheduled(cron = "1/5 * * * * *")  // 5秒钟执行一次
    public void task1(){
        System.err.println("*********   定时任务执行   **************");
        System.out.println(Thread.currentThread().getName());
        CopyOnWriteArraySet<WebSocketServer> webSocketSet = WebSocketServer.getWebSocketSet();
        System.out.println(webSocketSet);
        for (WebSocketServer webSocketServer : webSocketSet) {
            String message = "定时发送" + new Date();
            webSocketServer.sendMessage(message);
            list.add(message);
        }

        System.err.println("/n 定时任务完成.......");
    }

    @Scheduled(initialDelay=1000*60*5, fixedRate=1000*60*5)  // 5分钟后保存到excel
    public void task2() throws IOException {
        System.out.println("保存excel");
        System.out.println(Thread.currentThread().getName());
        Excel.saveExcel(list);
        list.clear();

    }

}
