package com.in28minutes.springbootrest.controller.post;

import com.in28minutes.springbootrest.controller.AbstractRestController;
import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.service.SurveyService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Basic controller to create a new survey
 */
@RestController
public class CreateNewSurveyQuestionController extends AbstractRestController {

  @Autowired
  public CreateNewSurveyQuestionController(SurveyService surveyService) {
    super(surveyService);
  }

  /**
   * Basic endpoint to add a new question to a specific survey.
   *
   * @param surveyId the survey id to find.
   * @param question the new question to add.
   */
  @PostMapping("/surveys/{survey_id}/questions")
  public ResponseEntity<Object> createNewSurveyQuestion(
      @PathVariable("survey_id") String surveyId,
      @RequestBody QuestionDto question) {

    String questionId = surveyService.addNewSurveyQuestion(surveyId, question);

    // Adds location as a header in the response.
    URI locationHeader = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{questionId}")
        .buildAndExpand(questionId)
        .toUri();

    return ResponseEntity
        .created(locationHeader)
        .build();
  }

}
