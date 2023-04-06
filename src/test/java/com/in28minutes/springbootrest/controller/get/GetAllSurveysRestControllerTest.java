package com.in28minutes.springbootrest.controller.get;

import static org.mockito.Mockito.when;

import com.in28minutes.springbootrest.controller.AbstractRestControllerTest;
import java.util.ArrayList;
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
@WebMvcTest(controllers = GetAllSurveysRestController.class)
class GetAllSurveysRestControllerTest extends AbstractRestControllerTest {

  @InjectMocks
  private GetAllSurveysRestController getAllSurveysController;

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

}
