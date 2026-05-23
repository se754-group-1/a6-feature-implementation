package com.example.learningapp.controller;

import com.example.learningapp.model.AnswerRequest;
import com.example.learningapp.model.AnswerResponse;
import com.example.learningapp.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FeedbackController.class)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Test
    public void postAnswer_shouldReturn200() throws Exception {
        AnswerRequest req = new AnswerRequest();
        req.setQuestionId("q1");
        req.setAnswer("A");
        req.setUserId("u1");

        AnswerResponse resp = new AnswerResponse(true, "Correct", "", "Next", true);
        when(feedbackService.evaluate(org.mockito.ArgumentMatchers.any())).thenReturn(resp);

        String json = "{\"questionId\":\"q1\",\"answer\":\"A\",\"userId\":\"u1\"}";

        mockMvc.perform(post("/api/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
