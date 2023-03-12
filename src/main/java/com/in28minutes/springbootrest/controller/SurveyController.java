package com.in28minutes.springbootrest.controller;

import com.in28minutes.springbootrest.entity.SurveyDto;
import com.in28minutes.springbootrest.service.SurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller class to manage the CRUD operations of the surveys.
 */
@RestController
public class SurveyController {

  private final SurveyService surveyService;

  @Autowired
  public SurveyController(SurveyService surveyService) {
    this.surveyService = surveyService;
  }

  /**
   * Basic endpoint to obtain all the surveys.
   *
   * @return list of surveys
   */
  @RequestMapping("/surveys")
  public List<SurveyDto> getAllSurveys() {

    return this.surveyService.getAllSurveys();
  }

  /**
   * Basic endpoint to obtain a specific survey with the id sent in the path.
   *
   * @param surveyId the servey id to find.
   * @return the survey with the given id.
   */
  @RequestMapping("/surveys/{id}")
  public SurveyDto getSurveyById(
      @PathVariable("id") String surveyId) {

    SurveyDto surveyFound = this.surveyService.getSurveyById(surveyId);
    if (surveyFound == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
    }

    return this.surveyService.getSurveyById(surveyId);
  }

}
