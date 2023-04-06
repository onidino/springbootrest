package com.in28minutes.springbootrest.integration.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.integration.AbstractRestControllerIT;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetAllQuestionsBySurveyIdRestControllerIT extends AbstractRestControllerIT {

  @Test
  void getQuestionsFromSurveyById_OK() throws JsonProcessingException {
    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_ALL_QUESTIONS, "Survey1"),
        HttpMethod.GET,
        requestEntity, String.class);

    List<QuestionDto> expectedResponseList = Arrays.asList(
        jsonMapper.readValue(responseEntity.getBody(), QuestionDto[].class));

    // assert
    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));

    Assertions.assertFalse(expectedResponseList.isEmpty());
    Assertions.assertTrue(expectedResponseList.stream()
        .anyMatch(question -> question.getId().equals("Question1")));
    Assertions.assertTrue(expectedResponseList.stream()
        .anyMatch(question -> question.getId().equals("Question2")));
    Assertions.assertTrue(expectedResponseList.stream()
        .anyMatch(question -> question.getId().equals("Question3")));
  }

  @Test
  void getQuestionsFromSurveyById_NotFound() {
    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_ALL_QUESTIONS, "NotFound"),
        HttpMethod.GET,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

}
