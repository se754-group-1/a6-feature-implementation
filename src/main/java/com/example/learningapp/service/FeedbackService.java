package com.example.learningapp.service;

import com.example.learningapp.model.AnswerRequest;
import com.example.learningapp.model.AnswerResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FeedbackService {

    private static class Question {
        String id;
        String correctAnswer;
        String explanation;
        String topic;

        Question(String id, String correctAnswer, String explanation, String topic) {
            this.id = id;
            this.correctAnswer = correctAnswer;
            this.explanation = explanation;
            this.topic = topic;
        }
    }

    private final Map<String, Question> questions = new HashMap<>();

    public FeedbackService() {
        // seed with a few sample questions for performance testing
        questions.put("q1", new Question("q1", "A", "The correct answer is A because... (polymorphism applies)", "OOP-Basics"));
        questions.put("q2", new Question("q2", "42", "42 is the expected literal value for this example.", "Intro-Examples"));
        questions.put("q3", new Question("q3", "true", "This expression evaluates to true because both operands are equal.", "Boolean-Expressions"));
    }

    public AnswerResponse evaluate(AnswerRequest request) {
        if (request == null || request.getQuestionId() == null) {
            return new AnswerResponse(false, "Invalid request", "No question id provided.", "Please provide a valid question id.", false);
        }

        Question q = questions.get(request.getQuestionId());
        if (q == null) {
            return new AnswerResponse(false, "Unknown question", "Question not found.", "Verify the question id or select another question.", false);
        }

        boolean correct = q.correctAnswer.equalsIgnoreCase(String.valueOf(request.getAnswer()));
        if (correct) {
            String msg = "Correct — well done.";
            String suggestion = "Proceed to the next topic or try a similar question on " + q.topic + ".";
            return new AnswerResponse(true, msg, "", suggestion, true);
        } else {
            String msg = "Incorrect — see explanation.";
            String suggestion = "Review the material for " + q.topic + " and try a similar question.";
            return new AnswerResponse(false, msg, q.explanation, suggestion, true);
        }
    }
}
