package com.in28minutes.springbootrest.controller.get;

import com.in28minutes.springbootrest.controller.AbstractRestController;
import com.in28minutes.springbootrest.entity.SurveyDto;
import com.in28minutes.springbootrest.service.SurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic controller to get all surveys
 */
@RestController
public class GetAllSurveysRestController extends AbstractRestController {

  @Autowired
  public GetAllSurveysRestController(SurveyService surveyService) {
    super(surveyService);
  }

  /**
   * Basic endpoint to obtain all the surveys.
   *
   * @return list of surveys
   */
  @GetMapping("/surveys")
  public List<SurveyDto> getAllSurveys() {

    return this.surveyService.getAllSurveys();
  }
}
