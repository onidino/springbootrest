package com.in28minutes.springbootrest.integration.get;

import com.in28minutes.springbootrest.integration.AbstractRestControllerIT;
import java.util.Objects;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetQuestionFromSurveyByIdRestControllerIT extends AbstractRestControllerIT {

  @Test
  void getQuestionFromSurveyById_OK() throws JSONException {
    String expectedResponse = """
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
        """;

    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "Survey1", "Question3"),
        HttpMethod.GET,
        requestEntity, String.class);

    // assert
    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
  }

  @Test
  void getQuestionFromSurveyById_NotFound() {
    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY_QUESTION, "Survey1", "NotFound"),
        HttpMethod.GET,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

}
