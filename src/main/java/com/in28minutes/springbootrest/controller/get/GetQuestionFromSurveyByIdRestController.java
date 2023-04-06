package com.in28minutes.springbootrest.controller.get;

import com.in28minutes.springbootrest.controller.AbstractRestController;
import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic controller to get a specific question found by survey id and question id
 */
@RestController
public class GetQuestionFromSurveyByIdRestController extends AbstractRestController {

  @Autowired
  public GetQuestionFromSurveyByIdRestController(SurveyService surveyService) {
    super(surveyService);
  }

  /**
   * Basic endpoint to obtain a specific survey with the id sent in the path.
   *
   * @param surveyId   the survey id to find.
   * @param questionId the question to find by id in the survey.
   * @return the survey with the given id.
   */
  @GetMapping("/surveys/{survey_id}/question/{question_id}")
  public QuestionDto getQuestionFromSurveyById(
      @PathVariable("survey_id") String surveyId,
      @PathVariable("question_id") String questionId) {

    QuestionDto questionFound = surveyService.getQuestionByIdFromSurveyById(surveyId, questionId);
    String questionIdFound = questionFound != null ? questionFound.getId() : null;

    checkQuestionNotFound(questionIdFound, surveyId, questionId);

    return questionFound;
  }
}