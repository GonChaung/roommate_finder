package com.abac.roommatefinder.dto;

import com.abac.roommatefinder.entity.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportRequest {
    @NotNull(message = "Reporter user ID is required")
    private Long reporterId;

    @NotNull(message = "Reported user ID is required")
    private Long reportedUserId;


    @NotBlank(message = "Reason is required")
    private String reason;

    private String description;

    @NotNull(message = "Report type is required")
    private ReportType type;
}