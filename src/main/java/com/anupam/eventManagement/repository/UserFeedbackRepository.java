package com.anupam.eventManagement.repository;

import com.anupam.eventManagement.model.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long> {
    List<UserFeedback> findByEventEventId(Long eventId);

}
