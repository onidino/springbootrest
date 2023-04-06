package com.in28minutes.springbootrest.controller.put;

import com.in28minutes.springbootrest.controller.AbstractRestController;
import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic controller to update a specific question from a survey found by id
 */
@RestController
public class UpdateSurveyQuestionByIdController extends AbstractRestController {

  @Autowired
  public UpdateSurveyQuestionByIdController(SurveyService surveyService) {
    super(surveyService);
  }

  /**
   * Basic endpoint to add a new question to a specific survey.
   *
   * @param surveyId the survey id to find.
   * @param question the new question to add.
   */
  @PutMapping("/surveys/{survey_id}/question/{question_id}")
  public ResponseEntity<Object> updateSurveyQuestion(
      @PathVariable("survey_id") String surveyId,
      @PathVariable("question_id") String questionId,
      @RequestBody QuestionDto question) {

    String questionUpdated = surveyService.updateSurveyQuestionById(
        surveyId, questionId, question);

    checkQuestionNotFound(questionUpdated, surveyId, questionId);

    return ResponseEntity
        .ok()
        .build();
  }

}
