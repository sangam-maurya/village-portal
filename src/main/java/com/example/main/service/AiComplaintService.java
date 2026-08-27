package com.example.main.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiComplaintService {

    private final Client client;

    public AiComplaintService(@Value("${gemini.api.key}") String apiKey) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String testGemini(String prompt) {

        String systemInstruction = """
            You are the AI Assistant for Village Portal.

            Your job is to help village residents with their questions
            and complaints related to village services and problems.

            Understand the user's message carefully and respond in
            simple Hindi or Hinglish.

            Be helpful, clear and concise.
            If the user is reporting a village problem, understand the
            problem and guide the user about the next appropriate step.
            """;

        String finalPrompt = systemInstruction + "\n\nUser message:\n" + prompt;

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.7-flash",
                        finalPrompt,
                        null
                );

        return response.text();
    }
}