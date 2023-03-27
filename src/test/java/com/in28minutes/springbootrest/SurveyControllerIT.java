package com.in28minutes.springbootrest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.entity.SurveyDto;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyControllerIT {

  private static final String URL_ALL_SURVEYS = "/surveys";
  private static final String URL_SURVEY = "/surveys/%s";
  private static final String URL_SURVEY_ALL_QUESTIONS = "/surveys/%s/questions";
  private static final String URL_SURVEY_QUESTION = "/surveys/%s/question/%s";

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void getAllSurveys_OK() throws JsonProcessingException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        URL_ALL_SURVEYS, String.class);

    List<SurveyDto> expectedResponseList = Arrays.asList(
        jsonMapper.readValue(responseEntity.getBody(), SurveyDto[].class));

    // assert
    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));

    Assertions.assertEquals(1, expectedResponseList.size());
    Assertions.assertEquals("Survey1", expectedResponseList.get(0).getId());
  }

  @Test
  void getSurveyById_OK() throws JSONException {
    String expectedResponse = """
            {
               "id": "Survey1",
               "title": "My Favorite Survey",
               "description": "Description of the Survey",
               "questions": [
                 {
                   "id": "Question1",
                   "description": "Most Popular Cloud Platform Today",
                   "options": [
                     "AWS",
                     "Azure",
                     "Google Cloud",
                     "Oracle Cloud"
                   ],
                   "correct_answer": "AWS"
                 },
                 {
                   "id": "Question2",
                   "description": "Fastest Growing Cloud Platform",
                   "options": [
                     "AWS",
                     "Azure",
                     "Google Cloud",
                     "Oracle Cloud"
                   ],
                   "correct_answer": "Google Cloud"
                 },
                 {
                   "id": "Question3",
                   "description": "Most Popular DevOps Tool",
                   "options": [
                     "Kubernetes",
                     "Docker",
                     "Terraform",
                     "Azure DevOps"
                   ],
                   "correct_answer": "Kubernetes"
                 }
               ]
             }
        """;

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY, "Survey1"), String.class);

    // assert
    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
  }

  @Test
  void getSurveyById_NotFound() {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY, "NotFound"), String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

  @Test
  void getQuestionsFromSurveyById_OK() throws JsonProcessingException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY_ALL_QUESTIONS, "Survey1"), String.class);

    List<QuestionDto> expectedResponseList = Arrays.asList(
        jsonMapper.readValue(responseEntity.getBody(), QuestionDto[].class));

    // assert
    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));

    Assertions.assertEquals(3, expectedResponseList.size());
    Assertions.assertEquals("Question1", expectedResponseList.get(0).getId());
    Assertions.assertEquals("Question2", expectedResponseList.get(1).getId());
    Assertions.assertEquals("Question3", expectedResponseList.get(2).getId());
  }

  @Test
  void getQuestionsFromSurveyById_NotFound() {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY_ALL_QUESTIONS, "NotFound"), String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

  @Test
  void getQuestionFromSurveyById_OK() throws JSONException {
    String expectedResponse = """
            {
               "id": "Question1",
               "description": "Most Popular Cloud Platform Today",
               "options": [
                 "AWS",
                 "Azure",
                 "Google Cloud",
                 "Oracle Cloud"
               ],
               "correct_answer": "AWS"
            }
        """;

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY_QUESTION, "Survey1", "Question1"), String.class);

    // assert
    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
  }

  @Test
  void getQuestionFromSurveyById_NotFound() {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY_QUESTION, "NotFound", "NotFound"), String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }
}
