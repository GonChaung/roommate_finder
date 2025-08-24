package com.abac.roommatefinder.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProfileRequest {
    @Min(16) @Max(100)
    private Integer age;
    private String gender;
    private String faculty;

    @NotBlank(message = "Lifestyle is required")
    private String lifestyle;

    @NotBlank(message = "Location is required")
    private String location;

    @Positive(message = "Budget must be positive")
    @NotNull(message = "Budget is required")
    private Double budget;

    private Boolean petFriendly;
}
