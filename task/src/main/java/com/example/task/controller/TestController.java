package com.example.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liq on 2019/5/6.
 */
@Controller
@RequestMapping("/test")
public class TestController {


  @GetMapping("/test01")
  public String test() {
    return "hello";
  }




}
