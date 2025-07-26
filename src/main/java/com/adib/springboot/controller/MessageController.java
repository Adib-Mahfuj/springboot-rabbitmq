package com.adib.springboot.controller;

import com.adib.springboot.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageController {

    private final RabbitMQProducer rabbitMQProducer;

    public MessageController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam String message)
    {
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message Sent to RabbitMQ");
    }
}
