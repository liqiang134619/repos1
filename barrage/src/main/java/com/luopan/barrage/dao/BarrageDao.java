package com.luopan.barrage.dao;

import com.luopan.barrage.modal.Barrage;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BarrageDao {

  List<Barrage> findGtId(long id);

  int insert(Barrage barrage);

}
