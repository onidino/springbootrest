package com.in28minutes.springbootrest.integration.post;

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
class CreateNewSurveyQuestionControllerIT extends AbstractRestControllerIT {

  @Test
  void createNewSurveyQuestion_OK() {
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

    HttpEntity<String> requestEntity = new HttpEntity<>(newQuestionBody, getHeadersWithAuth());

    // then
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_ALL_QUESTIONS, "Survey1"),
        HttpMethod.POST,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

}
