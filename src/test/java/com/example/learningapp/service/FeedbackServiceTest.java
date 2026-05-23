package com.example.learningapp.service;

import com.example.learningapp.model.AnswerRequest;
import com.example.learningapp.model.AnswerResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceTest {

    @Test
    public void evaluate_shouldReturnCorrectResponseForCorrectAnswer() {
        FeedbackService service = new FeedbackService();

        AnswerRequest request = new AnswerRequest();
        request.setQuestionId("q1");
        request.setAnswer("A");
        request.setUserId("u1");

        AnswerResponse response = service.evaluate(request);

        assertTrue(response.isCorrect());
        assertEquals("Correct — well done.", response.getMessage());
        assertTrue(response.getSuggestion().contains("next topic"));
        assertTrue(response.isAllowReattempt());
    }

    @Test
    public void evaluate_shouldReturnExplanationForIncorrectAnswer() {
        FeedbackService service = new FeedbackService();

        AnswerRequest request = new AnswerRequest();
        request.setQuestionId("q1");
        request.setAnswer("B");
        request.setUserId("u1");

        AnswerResponse response = service.evaluate(request);

        assertFalse(response.isCorrect());
        assertEquals("Incorrect — see explanation.", response.getMessage());
        assertFalse(response.getExplanation().isBlank());
        assertTrue(response.getSuggestion().contains("Review the material"));
        assertTrue(response.isAllowReattempt());
    }
}