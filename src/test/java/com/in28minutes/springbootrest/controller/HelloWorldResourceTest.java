package com.in28minutes.springbootrest.controller;

import com.in28minutes.springbootrest.entity.HelloWorldBeanDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = HelloWorldResourceTest.class)
class HelloWorldResourceTest {

  private HelloWorldResource helloWorldResource;

  @BeforeEach
  void init() {
    helloWorldResource = new HelloWorldResource();
  }

  @Test
  void testHelloWorld() {
    String result = helloWorldResource.helloWorld();

    Assertions.assertEquals("Hello World!", result);
  }

  @Test
  void helloWorldBean() {
    HelloWorldBeanDto result = helloWorldResource.helloWorldBean();

    Assertions.assertEquals("Hello World!", result.getMessage());
  }

  @Test
  void helloWorldBeanPathVariable() {
    HelloWorldBeanDto result = helloWorldResource.helloWorldBeanPathVariable("pepe");

    Assertions.assertEquals("Hello world pepe", result.getMessage());
  }

  @Test
  void helloWorldBeanMultiplePathVariable() {
    String name = "pepe";
    String message = "this_message";
    String expectedMessage = String.format("Hello world %s: %s", name, message);

    HelloWorldBeanDto result = helloWorldResource
        .helloWorldBeanMultiplePathVariable(name, message);

    Assertions.assertEquals(expectedMessage, result.getMessage());
  }

}