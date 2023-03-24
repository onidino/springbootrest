package com.in28minutes.springbootrest.controller;

import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.entity.SurveyDto;
import com.in28minutes.springbootrest.service.SurveyService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
  @GetMapping("/surveys")
  public List<SurveyDto> getAllSurveys() {

    return this.surveyService.getAllSurveys();
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
    if (questionFound == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          String.format("Question not found for survey id [%s] and question id [%s]",
              surveyId, questionId));
    }

    return questionFound;
  }

  /**
   * Basic endpoint to obtain all the questions from a specific survey.
   *
   * @param surveyId the survey id to find.
   */
  @PostMapping("/surveys/{survey_id}/questions")
  public ResponseEntity<Object> addNewSurveyQuestion(
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
