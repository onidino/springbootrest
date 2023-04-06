package com.in28minutes.springbootrest.controller;

import com.in28minutes.springbootrest.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractRestController {

  protected final SurveyService surveyService;

  @Autowired
  public AbstractRestController(SurveyService surveyService) {
    this.surveyService = surveyService;
  }

  protected void checkQuestionNotFound(
      final String questionFound, final String surveyId, final String questionId) {

    if (questionFound == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          String.format("Question not found for survey id [%s] and question id [%s]",
              surveyId, questionId));
    }
  }
}
