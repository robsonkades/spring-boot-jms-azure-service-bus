package com.example.servicebusdemo.controller;

import com.example.servicebusdemo.context.TenantContextHolder;
import com.example.servicebusdemo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SendController {

    private static final String QUEUE_NAME = "que001";
    private static final String TOPIC_NAME = "tpc001";

    private static final Logger LOGGER = LoggerFactory.getLogger(SendController.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/queue")
    public String postMessageQueue(@RequestParam String message) {
        String tenantId = UUID.randomUUID().toString();
        try {
            TenantContextHolder.setTenantId(tenantId);
            jmsTemplate.convertAndSend(QUEUE_NAME, new User(message), processor -> {
                processor.setStringProperty("tenant", TenantContextHolder.getTenantId());
                processor.setJMSPriority(9);
                return processor;
            });
            LOGGER.info("Sending message queue to tenant {}", tenantId);
        } finally {
            TenantContextHolder.clear();
        }
        return message;
    }

    @PostMapping("/topic")
    public String postMessageTopic(@RequestParam String message) {
        String tenantId = UUID.randomUUID().toString();
        try {
            TenantContextHolder.setTenantId(tenantId);
            jmsTemplate.convertAndSend(TOPIC_NAME, new User(message), processor -> {
                processor.setStringProperty("tenant", TenantContextHolder.getTenantId());
                processor.setJMSPriority(9);
                return processor;
            });
            LOGGER.info("Sending message topic to tenant {}", tenantId);
        } finally {
            TenantContextHolder.clear();
        }

        return message;
    }
}
