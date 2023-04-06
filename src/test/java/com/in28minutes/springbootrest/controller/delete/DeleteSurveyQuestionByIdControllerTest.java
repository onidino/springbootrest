package com.in28minutes.springbootrest.controller.delete;

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
@WebMvcTest(controllers = DeleteSurveyQuestionByIdController.class)
class DeleteSurveyQuestionByIdControllerTest extends AbstractRestControllerTest {

  @InjectMocks
  private DeleteSurveyQuestionByIdController deleteSurveyQuestionByIdController;

  @Test
  void test_deleteQuestionByIdFromSurveyById_ok() throws Exception {
    // when
    when(surveyService.deleteQuestionByIdFromSurveyById(anyString(), anyString()))
        .thenReturn("Question1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete(String.format(URL_SURVEY_QUESTION, "Survey1", "Question1"))
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    Assertions.assertEquals("", mvcResult.getResponse().getContentAsString());
  }

}