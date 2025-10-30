package com.example.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.SimpleChat;

@RestController
public class MyController {
    private static final String template = "Hello, %s!";
    private final SimpleChat simpleChat;

    public MyController(SimpleChat simpleChat) {
        this.simpleChat = simpleChat;
    }

    @PostMapping("/message")
    public String message(@RequestBody String name) {
        String nameClean = URLDecoder.decode(name, StandardCharsets.UTF_8);
        simpleChat.messageEvent(nameClean);
        return "Message sent :)"+ nameClean;
    }

    @GetMapping("/open")
    public String open() throws Exception {
        simpleChat.openChat();
        return "Chat opened!";
    }

    @GetMapping("/close")
    public String close() throws Exception {
        simpleChat.closeChat();
        return "Chat ended!";
    }

    @GetMapping("/ping")
    public String ping() {
        return "Service Up";
    }
}