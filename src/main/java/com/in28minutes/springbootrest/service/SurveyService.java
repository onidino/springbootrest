package com.in28minutes.springbootrest.service;

import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.entity.SurveyDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Simple service class with surveys information.
 */
@Service
public class SurveyService {

  private static final List<SurveyDto> SURVEY_LIST = new ArrayList<>();

  static {
    QuestionDto question1 = new QuestionDto(
        "Question1", "Most Popular Cloud Platform Today",
        Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"),
        "AWS");
    QuestionDto question2 = new QuestionDto(
        "Question2", "Fastest Growing Cloud Platform",
        Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"),
        "Google Cloud");
    QuestionDto question3 = new QuestionDto(
        "Question3", "Most Popular DevOps Tool",
        Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"),
        "Kubernetes");

    List<QuestionDto> questions = List.of(question1, question2, question3);

    SurveyDto survey = new SurveyDto("Survey1", "My Favorite Survey",
        "Description of the Survey", questions);

    SURVEY_LIST.add(survey);
  }

  /**
   * Returns a list with all the surveys.
   *
   * @return list of surveys
   */
  public List<SurveyDto> getAllSurveys() {

    return SURVEY_LIST;
  }

}
