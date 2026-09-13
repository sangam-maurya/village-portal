package com.example.main.controller;

import com.example.main.service.AiComplaintService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class AiComplaintController {

    private final AiComplaintService aiComplaintService;

    public AiComplaintController(AiComplaintService aiComplaintService) {
        this.aiComplaintService = aiComplaintService;
    }

//    @GetMapping("/test")
//    public String testGemini(@RequestParam String prompt) {
//        return aiComplaintService.testGemini(prompt);
//    }

    @PostMapping("/ask")
    public String askGemini(@RequestBody String prompt) {
        return aiComplaintService.testGemini(prompt);
    }
}