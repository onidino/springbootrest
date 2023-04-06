package com.in28minutes.springbootrest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.entity.SurveyDto;
import com.in28minutes.springbootrest.service.SurveyService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyController.class)
@AutoConfigureMockMvc(addFilters = false)
class SurveyControllerTest {

  private static final String BASE_URL = "http://localhost:8080";
  private static final String URL_ALL_SURVEYS = BASE_URL + "/surveys";
  private static final String URL_SURVEY = BASE_URL + "/surveys/%s";
  private static final String URL_SURVEY_ALL_QUESTIONS = BASE_URL + "/surveys/%s/questions";
  private static final String URL_SURVEY_QUESTION = BASE_URL + "/surveys/%s/question/%s";

  @MockBean
  private SurveyService surveyService;

  @InjectMocks
  private SurveyController surveyController;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void test_getAllSurveys_ok() throws Exception {
    // when
    when(surveyService.getAllSurveys())
        .thenReturn(new ArrayList<>());

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL_ALL_SURVEYS)
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
  }

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
    when(surveyService.getSurveyById(anyString()))
        .thenReturn(surveyDto);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(String.format(URL_SURVEY, "Survey1"))
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
    when(surveyService.getSurveyById(anyString()))
        .thenReturn(null);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(String.format(URL_SURVEY, "Survey1"))
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void test_getQuestionsFromSurveyById_ok() throws Exception {
    // given
    String expectedResponse = """
            [
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
            ]
        """;

    QuestionDto question1 = new QuestionDto(
        "Question1", "Most Popular Cloud Platform Today",
        Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"),
        "AWS");

    // when
    when(surveyService.getQuestionsFromSurveyById(anyString()))
        .thenReturn(List.of(question1));

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(String.format(URL_SURVEY_ALL_QUESTIONS, "Survey1"))
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
  }

  @Test
  void test_getQuestionsFromSurveyById_NotFound() throws Exception {
    // when
    when(surveyService.getQuestionsFromSurveyById(anyString()))
        .thenReturn(new ArrayList<>());

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(String.format(URL_SURVEY_ALL_QUESTIONS, "Survey1"))
        .accept(MediaType.APPLICATION_JSON);

    // then
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    // assert
    Assertions.assertNotNull(mvcResult);
    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
  }

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

  @Test
  void test_addNewSurveyQuestion_ok() throws Exception {
    // given
    String expectedLocationHeader = "http://localhost:8080/surveys/Survey1/questions/Question1";
    String requestBody = """
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

    // when
    when(surveyService.addNewSurveyQuestion(anyString(), any()))
        .thenReturn("Question1");

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
