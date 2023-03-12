package com.in28minutes.springbootrest.controller;

import com.in28minutes.springbootrest.entity.SurveyDto;
import com.in28minutes.springbootrest.service.SurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @RequestMapping("/surveys")
  public List<SurveyDto> getAllSurveys() {

    return this.surveyService.getAllSurveys();
  }

}
