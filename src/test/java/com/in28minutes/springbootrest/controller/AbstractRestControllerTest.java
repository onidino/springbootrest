package com.in28minutes.springbootrest.controller;

import com.in28minutes.springbootrest.service.SurveyService;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

public abstract class AbstractRestControllerTest {

  protected static final String BASE_URL = "http://localhost:8080";
  protected static final String URL_ALL_SURVEYS = BASE_URL + "/surveys";
  protected static final String URL_SURVEY = BASE_URL + "/surveys/%s";
  protected static final String URL_SURVEY_ALL_QUESTIONS = BASE_URL + "/surveys/%s/questions";
  protected static final String URL_SURVEY_QUESTION = BASE_URL + "/surveys/%s/question/%s";

  private static final String AUTH_USER = "admin";
  private static final String AUTH_PASSWORD = "password";

  @MockBean
  protected SurveyService surveyService;

  @Autowired
  protected MockMvc mockMvc;

  private String getAuthorizedCredentials() {

    String credentials = AUTH_USER + ":" + AUTH_PASSWORD;
    byte[] encoded = Base64.getEncoder().encode(credentials.getBytes());
    return new String(encoded);
  }

  protected HttpHeaders getHeadersWithAuth() {

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-Type", "application/json");
    httpHeaders.add("Authorization", "Basic " + getAuthorizedCredentials());
    return httpHeaders;
  }
}
