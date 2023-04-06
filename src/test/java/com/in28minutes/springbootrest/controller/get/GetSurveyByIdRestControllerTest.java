package com.in28minutes.springbootrest.controller.get;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.in28minutes.springbootrest.controller.AbstractRestControllerTest;
import com.in28minutes.springbootrest.entity.SurveyDto;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = GetSurveyByIdRestController.class)
class GetSurveyByIdRestControllerTest extends AbstractRestControllerTest {

  @InjectMocks
  private GetSurveyByIdRestController getSurveyByIdController;

  @Test
  void test_getSurveyById_ok() throws Exception {
    // given
    String expectedResponse = """
            {
               "id": "Survey1",
               "title": "My Favorite Survey",
               "description": "Description of the Survey",
               "questions": []
             }
        """;

    SurveyDto surveyDto = new SurveyDto("Survey1", "My Favorite Survey",
        "Description of the Survey", new ArrayList<>());

    // when
    when(surveyService.getSurveyById(anyString())).thenReturn(surveyDto);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(String.format(URL_SURVEY, "Survey1"))
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
  }

  @Test
  void test_getSurveyById_NotFound() throws Exception {
    // when
    when(surveyService.getSurveyById(anyString())).thenReturn(null);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(String.format(URL_SURVEY, "Survey1"))
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
  }

}