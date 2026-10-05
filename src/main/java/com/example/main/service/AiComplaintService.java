package com.example.main.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiComplaintService {

    private final Client client;
    private final ChatClient chatClient;
    public AiComplaintService(@Value("${gemini.api.key}") String apiKey, ChatClient chatClient) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
        this.chatClient = chatClient;
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
    public String chat(String message) {

        String prompt = """
            You are Village Portal AI Assistant.

            Always reply in simple Hindi or Hinglish.
            Do not reply in English unless the user specifically asks for English.
            You are helping Indian village residents.
            Keep your answer short, simple and helpful.

            User message:
            """ + message;

        String response = chatClient.call(message);

        System.out.println("AI RESPONSE = " + response);

        return response;
    }

}