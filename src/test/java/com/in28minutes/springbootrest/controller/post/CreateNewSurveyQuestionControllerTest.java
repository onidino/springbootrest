package com.in28minutes.springbootrest.controller.post;

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
@WebMvcTest(controllers = CreateNewSurveyQuestionController.class)
class CreateNewSurveyQuestionControllerTest extends AbstractRestControllerTest {

  @InjectMocks
  private CreateNewSurveyQuestionController createNewSurveyQuestionController;

  @Test
  void test_addNewSurveyQuestion_ok() throws Exception {
    // given
    String expectedLocationHeader = "http://localhost:8080/surveys/Survey1/questions/Question1234";
    String requestBody = """
        {
           "id": "Question1234",
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

    // when
    when(surveyService.addNewSurveyQuestion(anyString(), any()))
        .thenReturn("Question1234");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(String.format(URL_SURVEY_ALL_QUESTIONS, "Survey1"))
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String resultLocationHeader = mvcResult.getResponse().getHeaders("location").get(0);

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    Assertions.assertEquals(expectedLocationHeader, resultLocationHeader);
  }

}
