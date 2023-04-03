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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SurveyControllerIT {

  private static final String URL_ALL_SURVEYS = "/surveys";
  private static final String URL_SURVEY = "/surveys/%s";
  private static final String URL_SURVEY_ALL_QUESTIONS = "/surveys/%s/questions";
  private static final String URL_SURVEY_QUESTION = "/surveys/%s/question/%s";

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Order(1)
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
  @Order(2)
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
  @Order(3)
  void getSurveyById_NotFound() {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY, "NotFound"), String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

  @Test
  @Order(4)
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
  @Order(5)
  void getQuestionsFromSurveyById_NotFound() {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY_ALL_QUESTIONS, "NotFound"), String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

  @Test
  @Order(6)
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
  @Order(7)
  void addNewSurveyQuestion_OK() {
    // given
    String newQuestionBody = """
        {
            "description": "Your Favorite Cloud Platform",
            "options": [
                "AWS",
                "Azure",
                "Google Cloud",
                "Oracle Cloud"
            ],
            "correctAnswer": "Google Cloud"
        }
        """;

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-Type", "application/json");
    HttpEntity<String> requestEntity = new HttpEntity<>(newQuestionBody, httpHeaders);

    // then
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_ALL_QUESTIONS, "Survey"),
        HttpMethod.POST,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  @Order(8)
  void getQuestionFromSurveyById_NotFound() {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        String.format(URL_SURVEY_QUESTION, "NotFound", "NotFound"), String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

  @Test
  @Order(9)
  void updateSurveyQuestion_OK() {
    // given
    String newQuestionBody = """
        {
            "id": "Question1",
            "description": "Updated description",
            "options": [
                "AWS",
                "Azure",
                "Google Cloud",
                "Oracle Cloud"
            ],
            "correctAnswer": "Google Cloud"
        }
        """;

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-Type", "application/json");
    HttpEntity<String> requestEntity = new HttpEntity<>(newQuestionBody, httpHeaders);

    // then
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "Survey1", "Question1"),
        HttpMethod.PUT,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  @Order(10)
  void deleteQuestionByIdFromSurveyById_OK() {
    ResponseEntity<Void> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "Survey1", "Question1"),
        HttpMethod.DELETE,
        null, Void.class);

    // assert
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  @Order(11)
  void deleteQuestionByIdFromSurveyById_NotFound() {
    ResponseEntity<Void> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "NotFound", "NotFound"),
        HttpMethod.DELETE,
        null, Void.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

}
