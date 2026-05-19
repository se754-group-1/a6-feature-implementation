package com.example.learningapp.controller;

import com.example.learningapp.model.AnswerRequest;
import com.example.learningapp.model.AnswerResponse;
import com.example.learningapp.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/answer")
    public ResponseEntity<AnswerResponse> submitAnswer(@RequestBody AnswerRequest request) {
        AnswerResponse response = feedbackService.evaluate(request);
        return ResponseEntity.ok(response);
    }
}
