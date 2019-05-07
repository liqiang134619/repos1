package com.luopan.barrage.service.impl;

import com.luopan.barrage.dao.BarrageDao;
import com.luopan.barrage.modal.Barrage;
import com.luopan.barrage.service.IBarrageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BarrageService implements IBarrageService {

  @Autowired
  private BarrageDao barrageDao;

  @Override
  public List<Barrage> findGtId(long id) {
    return barrageDao.findGtId(id);
  }

  @Override
  public int insert(Barrage barrage) {
    return barrageDao.insert(barrage);
  }
}
