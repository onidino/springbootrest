package com.in28minutes.springbootrest.controller.get;

import com.in28minutes.springbootrest.controller.AbstractRestController;
import com.in28minutes.springbootrest.entity.SurveyDto;
import com.in28minutes.springbootrest.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Basic controller to get all surveys by id
 */
@RestController
public class GetSurveyByIdRestController extends AbstractRestController {

  @Autowired
  public GetSurveyByIdRestController(SurveyService surveyService) {
    super(surveyService);
  }

  /**
   * Basic endpoint to obtain a specific survey with the id sent in the path.
   *
   * @param surveyId the servey id to find.
   * @return the survey with the given id.
   */
  @GetMapping("/surveys/{id}")
  public SurveyDto getSurveyById(
      @PathVariable("id") String surveyId) {

    SurveyDto surveyFound = surveyService.getSurveyById(surveyId);
    if (surveyFound == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          String.format("Survey not found for id [%s]", surveyId));
    }

    return surveyFound;
  }

}


