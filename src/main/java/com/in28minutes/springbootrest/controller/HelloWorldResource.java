package com.in28minutes.springbootrest.controller;

import com.in28minutes.springbootrest.entity.HelloWorldBeanDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple RestController for the hello world entrypoints.
 */
@RestController
public class HelloWorldResource {

  @RequestMapping("/hello-world")
  public String helloWorld() {

    return "Hello World!";
  }

  @RequestMapping("/hello-world-bean")
  public HelloWorldBeanDto helloWorldBean() {

    return new HelloWorldBeanDto("Hello World!");
  }

  @RequestMapping("/hello-world-path-param/{name}")
  public HelloWorldBeanDto helloWorldBeanPathVariable(
      @PathVariable String name) {

    return new HelloWorldBeanDto(String.format("Hello world %s", name));
  }

  @RequestMapping("/hello-world-path-param/{name}/message/{message}")
  public HelloWorldBeanDto helloWorldBeanMultiplePathVariable(
      @PathVariable("name") String name, @PathVariable("message") String message) {

    return new HelloWorldBeanDto(String.format("Hello world %s: %s", name, message));
  }
}
