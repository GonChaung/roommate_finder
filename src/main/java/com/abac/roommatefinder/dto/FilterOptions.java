package com.abac.roommatefinder.dto;

import lombok.Data;

@Data
public class FilterOptions {
    private String location;
    private String lifestyle;
    private Double minBudget;
    private Double maxBudget;
    private Boolean petFriendly;
}
