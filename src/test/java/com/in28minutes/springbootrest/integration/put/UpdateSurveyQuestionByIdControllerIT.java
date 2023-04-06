package com.in28minutes.springbootrest.integration.put;

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
class UpdateSurveyQuestionByIdControllerIT extends AbstractRestControllerIT {

  @Test
  void updateSurveyQuestion_OK() {
    // given
    String newQuestionBody = """
        {
            "id": "Question2",
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

    HttpEntity<String> requestEntity = new HttpEntity<>(newQuestionBody, getHeadersWithAuth());

    // then
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "Survey1", "Question2"),
        HttpMethod.PUT,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void updateSurveyQuestion_NotFound() {
    // given
    String newQuestionBody = """
        {}
        """;

    HttpEntity<String> requestEntity = new HttpEntity<>(newQuestionBody, getHeadersWithAuth());

    // then
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "Survey1", "QuestionX"),
        HttpMethod.PUT,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

}
