package com.in28minutes.springbootrest.service;

import com.in28minutes.springbootrest.entity.QuestionDto;
import com.in28minutes.springbootrest.entity.SurveyDto;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    List<QuestionDto> questions = new ArrayList<>(List.of(question1, question2, question3));

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

  /**
   * Returns a specific survey with the id passed as parameter.
   *
   * @return a survey
   */
  public SurveyDto getSurveyById(
      final String id) {

    return SURVEY_LIST.stream()
        .filter(survey -> survey.getId().equalsIgnoreCase(id))
        .findFirst()
        .orElse(null);
  }

  /**
   * Returns a specific question with the id passed as param from the survey id passed as param.
   *
   * @param surveyId survey id to find.
   * @return a list of questions of the survey found.
   */
  public List<QuestionDto> getQuestionsFromSurveyById(
      final String surveyId) {

    Optional<SurveyDto> surveyFound = SURVEY_LIST.stream()
        .filter(survey -> survey.getId().equalsIgnoreCase(surveyId))
        .findFirst();
    if (surveyFound.isPresent()) {
      return surveyFound.get().getQuestions();
    }

    return Collections.emptyList();
  }

  /**
   * Returns a specific question by id from the survey found by id.
   *
   * @param surveyId   survey id to find.
   * @param questionId question id to find.
   * @return a question
   */
  public QuestionDto getQuestionByIdFromSurveyById(
      final String surveyId, final String questionId) {

    Optional<SurveyDto> surveyFound = SURVEY_LIST.stream()
        .filter(survey -> survey.getId().equalsIgnoreCase(surveyId))
        .findFirst();

    return surveyFound.flatMap(surveyDto -> surveyDto.getQuestions().stream()
            .filter(question -> question.getId().equalsIgnoreCase(questionId))
            .findFirst())
        .orElse(null);
  }

  /**
   * Method to add a new question to the specific survey found by id.
   *
   * @param surveyId the survey id to find the survey
   * @param question the new question to add
   */
  public String addNewSurveyQuestion(
      final String surveyId, final QuestionDto question) {

    List<QuestionDto> questionsList = getQuestionsFromSurveyById(surveyId);
    question.setId(generateRandomId());
    questionsList.add(question);

    return question.getId();
  }

  private String generateRandomId() {
    return new BigInteger(32, new SecureRandom())
        .toString();
  }
}
