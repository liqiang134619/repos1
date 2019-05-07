package com.luopan.barrage;

import com.luopan.barrage.websocket.WebSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@EnableScheduling
@MapperScan("com.luopan.barrage.dao")
@SpringBootApplication
public class BarrageApplication {

  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BarrageApplication.class, args);
    // 解决websocket属性无法注入的问题
    WebSocket.setPropertyByApplicationContext(configurableApplicationContext);
  }

}

