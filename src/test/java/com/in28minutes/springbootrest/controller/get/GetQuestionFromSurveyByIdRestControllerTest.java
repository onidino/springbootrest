package com.in28minutes.springbootrest.controller.get;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.in28minutes.springbootrest.controller.AbstractRestControllerTest;
import com.in28minutes.springbootrest.entity.QuestionDto;
import java.util.Arrays;
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
@WebMvcTest(controllers = GetQuestionFromSurveyByIdRestController.class)
class GetQuestionFromSurveyByIdRestControllerTest extends AbstractRestControllerTest {

  @InjectMocks
  private GetQuestionFromSurveyByIdRestController getQuestionFromSurveyByIdController;

  @Test
  void test_getQuestionFromSurveyById_ok() throws Exception {
    // given
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

    QuestionDto question1 = new QuestionDto(
        "Question1", "Most Popular Cloud Platform Today",
        Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"),
        "AWS");

    // when
    when(surveyService.getQuestionByIdFromSurveyById(anyString(), anyString()))
        .thenReturn(question1);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(String.format(URL_SURVEY_QUESTION, "Survey1", "Question1"))
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
  }

  @Test
  void test_getQuestionFromSurveyById_NotFound() throws Exception {
    // when
    when(surveyService.getQuestionByIdFromSurveyById(anyString(), anyString()))
        .thenReturn(null);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(String.format(URL_SURVEY_QUESTION, "Survey1", "Question1"))
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
  }

}
