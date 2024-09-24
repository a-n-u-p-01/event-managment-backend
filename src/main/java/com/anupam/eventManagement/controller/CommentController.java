package com.anupam.eventManagement.controller;

import com.anupam.eventManagement.entity.Comment;
import com.anupam.eventManagement.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @PostMapping
    public ResponseEntity addComment(@RequestBody Comment comment){

        return new ResponseEntity<>(commentRepository.save(comment),HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    private ResponseEntity<List<Comment>> getAllComment(@PathVariable Long eventId){

        return new ResponseEntity<>( commentRepository.findAllByEventId(eventId),HttpStatus.OK);
    }

}
