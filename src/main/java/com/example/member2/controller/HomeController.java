package com.example.member2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home() { // .../으로 접속하면
    return "/home"; // home.html으로 이동
  }
}
