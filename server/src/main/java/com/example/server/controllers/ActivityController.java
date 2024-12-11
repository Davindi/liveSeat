package com.example.server.controllers;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActivityController {

    private final SimpMessagingTemplate messagingTemplate;

    public ActivityController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @GetMapping("/testActivity")
    public String testActivity() {
        String activity = "Test activity log";
        broadcastActivity(activity);
        return "Activity broadcasted!";
    }
    public void broadcastActivity(String activity) {
        System.out.println("activity"+activity);
        messagingTemplate.convertAndSend("/topic/activities", activity);


    }
}


