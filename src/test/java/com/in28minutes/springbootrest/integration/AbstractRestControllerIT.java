package com.in28minutes.springbootrest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public abstract class AbstractRestControllerIT {

  protected static final String URL_ALL_SURVEYS = "/surveys";
  protected static final String URL_SURVEY = "/surveys/%s";
  protected static final String URL_SURVEY_ALL_QUESTIONS = "/surveys/%s/questions";
  protected static final String URL_SURVEY_QUESTION = "/surveys/%s/question/%s";

  private static final String AUTH_USER = "admin";
  private static final String AUTH_PASSWORD = "password";

  protected final ObjectMapper jsonMapper = new ObjectMapper();

  protected HttpEntity<String> requestEntity;

  @Autowired
  protected TestRestTemplate restTemplate;

  private static String getAuthorizedCredentials() {

    String credentials = AUTH_USER + ":" + AUTH_PASSWORD;
    byte[] encoded = Base64.getEncoder().encode(credentials.getBytes());
    return new String(encoded);
  }

  protected static HttpHeaders getHeadersWithAuth() {

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-Type", "application/json");
    httpHeaders.add("Authorization", "Basic " + getAuthorizedCredentials());
    return httpHeaders;
  }

}
