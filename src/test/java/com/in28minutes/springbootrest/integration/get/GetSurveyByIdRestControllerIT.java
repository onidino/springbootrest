package com.in28minutes.springbootrest.integration.get;

import com.in28minutes.springbootrest.integration.AbstractRestControllerIT;
import java.util.Objects;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GetSurveyByIdRestControllerIT extends AbstractRestControllerIT {

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
                    },
                    {
                        "id": "QuestionForTest",
                        "description": "test",
                        "options": [
                            "test",
                            "test",
                            "test",
                            "test"
                        ],
                        "correct_answer": "test"
                    }
                ]
             }
        """;

    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY, "Survey1"),
        HttpMethod.GET,
        requestEntity, String.class);

    // assert
    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
  }

  @Test
  void getSurveyById_NotFound() {
    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        String.format(URL_SURVEY, "NotFound"),
        HttpMethod.GET,
        requestEntity, String.class);

    // assert
    Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

}
