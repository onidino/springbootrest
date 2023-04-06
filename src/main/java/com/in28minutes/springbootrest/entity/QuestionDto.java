package com.in28minutes.springbootrest.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple Question dto with description of the question and the correct answer.
 */
@Data
@NoArgsConstructor
public class QuestionDto {

  private String id;
  private String description;
  private List<String> options;
  private String correctAnswer;

  @JsonCreator
  public QuestionDto(
      @JsonProperty("id") String id,
      @JsonProperty("description") String description,
      @JsonProperty("options") List<String> options,
      @JsonProperty("correct_answer") String correctAnswer) {
    this.id = id;
    this.description = description;
    this.options = options;
    this.correctAnswer = correctAnswer;
  }

  @Override
  public String toString() {
    return "QuestionDto{" +
        "id='" + id + '\'' +
        ", description='" + description + '\'' +
        ", options=" + options +
        ", correctAnswer='" + correctAnswer + '\'' +
        '}';
  }
}
