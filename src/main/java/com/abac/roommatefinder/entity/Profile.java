package com.abac.roommatefinder.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "profiles")
@Data
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;
    private String gender;
    private String faculty;
    private String lifestyle;
    private String location;
    private Double budget;
    private Boolean petFriendly;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public Profile() {}
}
