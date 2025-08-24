package com.abac.roommatefinder.dto;

import lombok.Data;

@Data
public class FilterOptions {
    private String location;
    private String lifestyle;
    private Double minBudget;
    private Double maxBudget;
    private Boolean petFriendly;
/*
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getLifestyle() { return lifestyle; }
    public void setLifestyle(String lifestyle) { this.lifestyle = lifestyle; }
    public Double getMinBudget() { return minBudget; }
    public void setMinBudget(Double minBudget) { this.minBudget = minBudget; }
    public Double getMaxBudget() { return maxBudget; }
    public void setMaxBudget(Double maxBudget) { this.maxBudget = maxBudget; }
    public Boolean getPetFriendly() { return petFriendly; }
    public void setPetFriendly(Boolean petFriendly) { this.petFriendly = petFriendly; }
*/
}
