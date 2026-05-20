package com.example.learningapp.model;

public class AnswerResponse {
    private boolean correct;
    private String message;
    private String explanation;
    private String suggestion;
    private boolean allowReattempt;

    public AnswerResponse() {}

    public AnswerResponse(boolean correct, String message, String explanation, String suggestion, boolean allowReattempt) {
        this.correct = correct;
        this.message = message;
        this.explanation = explanation;
        this.suggestion = suggestion;
        this.allowReattempt = allowReattempt;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public boolean isAllowReattempt() {
        return allowReattempt;
    }

    public void setAllowReattempt(boolean allowReattempt) {
        this.allowReattempt = allowReattempt;
    }
}
