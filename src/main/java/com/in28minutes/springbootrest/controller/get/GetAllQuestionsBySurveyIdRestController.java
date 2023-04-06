package com.in28minutes.springbootrest.controller.get;

import com.in28minutes.springbootrest.controller.AbstractRestController;
import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.service.SurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Basic controller to get all questions from a survey found by id
 */
@RestController
public class GetAllQuestionsBySurveyIdRestController extends AbstractRestController {

  @Autowired
  public GetAllQuestionsBySurveyIdRestController(SurveyService surveyService) {
    super(surveyService);
  }

  /**
   * Basic endpoint to obtain all the questions from a specific survey.
   *
   * @param surveyId the survey id to find.
   * @return the survey with the given id.
   */
  @GetMapping("/surveys/{survey_id}/questions")
  public List<QuestionDto> getQuestionsFromSurveyById(
      @PathVariable("survey_id") String surveyId) {

    List<QuestionDto> questionsList = surveyService.getQuestionsFromSurveyById(surveyId);
    if (questionsList.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          String.format("Questions not found for survey id [%s]", surveyId));
    }

    return questionsList;
  }

}
