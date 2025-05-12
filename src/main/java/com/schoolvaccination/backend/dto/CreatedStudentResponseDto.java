package com.schoolvaccination.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreatedStudentResponseDto {
    @JsonProperty("student_id")
    private Long id;
    @JsonProperty("student_name")
    private String name;

}
