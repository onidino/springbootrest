package com.in28minutes.springbootrest.controller.delete;

import com.in28minutes.springbootrest.controller.AbstractRestController;
import com.in28minutes.springbootrest.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic controller to delete a question by id from a survey found by id.
 */
@RestController
public class DeleteSurveyQuestionByIdController extends AbstractRestController {

  @Autowired
  public DeleteSurveyQuestionByIdController(SurveyService surveyService) {
    super(surveyService);
  }

  /**
   * Basic endpoint to delete a specific question from a survey found by id.
   *
   * @param surveyId   the survey id to find.
   * @param questionId the question to find and delete.
   * @return the question id of the deleted question.
   */
  @DeleteMapping("/surveys/{survey_id}/question/{question_id}")
  public ResponseEntity<Object> deleteQuestionByIdFromSurveyById(
      @PathVariable("survey_id") String surveyId,
      @PathVariable("question_id") String questionId) {

    String deletedQuestionId = surveyService.deleteQuestionByIdFromSurveyById(surveyId, questionId);

    checkQuestionNotFound(deletedQuestionId, surveyId, questionId);

    return ResponseEntity
        .ok()
        .build();
  }

}
