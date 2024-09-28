package com.anupam.eventManagement.repository;

import com.anupam.eventManagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c WHERE c.eventId = ?1")
    List<Comment> findAllByEventId(Long eventId);
}
