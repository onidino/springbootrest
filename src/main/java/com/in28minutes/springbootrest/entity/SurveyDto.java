package com.in28minutes.springbootrest.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Simple Survey class with fields and lists of questions.
 */
@Getter
@NoArgsConstructor
public class SurveyDto {

  private String id;
  private String title;
  private String description;
  private List<QuestionDto> questions;

  @JsonCreator
  public SurveyDto(
      @JsonProperty("id") String id,
      @JsonProperty("title") String title,
      @JsonProperty("description") String description,
      @JsonProperty("questions") List<QuestionDto> questions) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.questions = questions;
  }

  @Override
  public String toString() {
    return "SurveyDto{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", questions=" + questions +
        '}';
  }
}
