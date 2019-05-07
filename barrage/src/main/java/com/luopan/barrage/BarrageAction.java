package com.luopan.barrage;

import com.luopan.barrage.modal.Barrage;
import com.luopan.barrage.service.IBarrageService;
import com.luopan.barrage.websocket.WebSocket;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BarrageAction {

  @Autowired
  private WebSocket webSocket;

  @Autowired
  private IBarrageService barrageService;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  /**
   * 推送弹幕到客户端
   */
  @Scheduled(fixedRate = 2000L)
  public void sendBarrage() {
    long barrageId = getBarrageId();
    List<Barrage> list = barrageService.findGtId(barrageId);
    if (list != null && !list.isEmpty()) {
      webSocket.sendMessage(list);
      stringRedisTemplate.opsForValue().set("barrageId", list.get(list.size() - 1).getId().toString());
    }
  }

  /**
   * 新增弹幕
   */
  @Scheduled(fixedRate = 2000L)
  public void insertBarrage() {
    Random random = new Random();
    Barrage barrage = new Barrage();
    barrage.setMessage("测试弹幕" + random.nextInt(10));
    barrageService.insert(barrage);
  }

  private Long getBarrageId() {
    long barrageId = 0L;
    String barrageIdStr = stringRedisTemplate.opsForValue().get("barrageId");
    if (barrageIdStr != null) {
      barrageId = Long.parseLong(barrageIdStr);
    }
    return barrageId;
  }

}
