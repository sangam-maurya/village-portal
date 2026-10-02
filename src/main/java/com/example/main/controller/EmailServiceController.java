package com.example.main.controller;

import com.example.main.service.EmaillService;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Text;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin(origins = "*")
public class EmailServiceController {

    private final EmaillService emaillService;

    public EmailServiceController(EmaillService emaillService) {
        this.emaillService = emaillService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to ,
                            @RequestParam String subject, @RequestParam String text){
        emaillService.sendMsg(to , subject , text);
        return "email send succesfully";
    }
}
