package com.in28minutes.springbootrest.integration.delete;

import com.in28minutes.springbootrest.integration.AbstractRestControllerIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DeleteSurveyQuestionByIdRestControllerIT extends AbstractRestControllerIT {

  @Test
  void deleteQuestionByIdFromSurveyById_OK() {
    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "Survey1", "QuestionForTest"),
        HttpMethod.DELETE,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void deleteQuestionByIdFromSurveyById_NotFound() {
    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "NotFound", "NotFound"),
        HttpMethod.GET,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

}
