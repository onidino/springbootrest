package com.in28minutes.springbootrest.integration.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.in28minutes.springbootrest.entity.SurveyDto;
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
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetAllSurveysRestControllerIT extends AbstractRestControllerIT {

  @Test
  void getAllSurveys_OK() throws JsonProcessingException {
    requestEntity = new HttpEntity<>(null, getHeadersWithAuth());

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        URL_ALL_SURVEYS,
        HttpMethod.GET,
        requestEntity, String.class);

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

}
