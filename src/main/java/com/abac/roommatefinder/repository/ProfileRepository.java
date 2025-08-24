package com.abac.roommatefinder.repository;

import com.abac.roommatefinder.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long userId);
    List<Profile> findByLocationIgnoreCase(String location);
}
