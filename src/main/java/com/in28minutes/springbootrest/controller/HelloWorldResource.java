package com.in28minutes.springbootrest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldResource {

  @ResponseBody
  @RequestMapping("/hello-world")
  public String helloWorld(){
    return "Hello World!";
  }

}
