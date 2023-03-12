package com.in28minutes.springbootrest.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Simple Dto with for the hello-world entrypoints.
 */
@Getter
public class HelloWorldBeanDto {

  private final String message;

  @JsonCreator
  public HelloWorldBeanDto(@JsonProperty("message") String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "HelloWorldBean{" +
        "message='" + message + '\'' +
        '}';
  }
}
