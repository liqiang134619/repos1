package com.luopan.barrage.modal;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Barrage implements Serializable {

  private static final long serialVersionUID = -4716080074837370713L;
  private Long id;
  private String message;

}
