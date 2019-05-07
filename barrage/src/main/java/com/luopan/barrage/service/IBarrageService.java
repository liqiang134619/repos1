package com.luopan.barrage.service;

import com.luopan.barrage.modal.Barrage;
import java.util.List;

public interface IBarrageService {

  List<Barrage> findGtId(long id);

  int insert(Barrage barrage);

}
