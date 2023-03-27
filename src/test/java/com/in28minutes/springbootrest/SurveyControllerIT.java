package com.in28minutes.springbootrest;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyControllerIT {

  private static final String URL_SURVEY_QUESTION = "/surveys/Survey1/question/Question1";
  @Autowired
  private TestRestTemplate template;

  @Test
  void retrieveSpecificSurveyQuestion_scenario() throws JSONException {
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

    ResponseEntity<String> responseEntity = template.getForEntity(
        URL_SURVEY_QUESTION, String.class);

    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    Assertions.assertEquals("application/json",
        Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
  }

}
