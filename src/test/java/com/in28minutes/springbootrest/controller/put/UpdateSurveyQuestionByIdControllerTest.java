package com.in28minutes.springbootrest.controller.put;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.in28minutes.springbootrest.controller.AbstractRestControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UpdateSurveyQuestionByIdController.class)
class UpdateSurveyQuestionByIdControllerTest extends AbstractRestControllerTest {

  @InjectMocks
  private UpdateSurveyQuestionByIdController updateSurveyQuestionByIdController;

  @Test
  void test_updateSurveyQuestion_ok() throws Exception {
    // when
    String requestBody = """
        {
           "id": "Question1",
           "description": "UPDATED DESCRIPTION",
           "options": [
             "AWS",
             "Azure",
             "Google Cloud",
             "Oracle Cloud"
           ],
           "correct_answer": "AWS"
         }
        """;

    when(surveyService.updateSurveyQuestionById(anyString(), anyString(), any()))
        .thenReturn("Question1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put(String.format(URL_SURVEY_QUESTION, "Survey1", "Question1"))
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    Assertions.assertEquals("", mvcResult.getResponse().getContentAsString());
  }

  @Test
  void test_updateSurveyQuestion_NotFound() throws Exception {
    // when
    String requestBody = """
        {
           "id": "Question1",
           "description": "UPDATED DESCRIPTION",
           "options": [
             "AWS",
             "Azure",
             "Google Cloud",
             "Oracle Cloud"
           ],
           "correct_answer": "AWS"
         }
        """;

    when(surveyService.updateSurveyQuestionById(anyString(), anyString(), any()))
        .thenReturn(null);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put(String.format(URL_SURVEY_QUESTION, "Survey1", "Question1"))
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    Assertions.assertEquals("", mvcResult.getResponse().getContentAsString());
  }

}