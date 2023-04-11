package com.example.servicebusdemo.controller;

import com.example.servicebusdemo.annotation.QueueListener;
import com.example.servicebusdemo.annotation.TopicListener;
import com.example.servicebusdemo.context.TenantContextHolder;
import com.example.servicebusdemo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReceiveService {

    private static final String QUEUE_NAME = "que001";
    private static final String TOPIC_NAME = "tpc001";
    private static final String SUBSCRIPTION_NAME = "sub001";

    private final Logger LOGGER = LoggerFactory.getLogger(ReceiveService.class);

    @QueueListener(destination = QUEUE_NAME)
    public void receiveMessageQueue(User user) {
        LOGGER.info("Received message queue: {} tenant {}", user.getName(), TenantContextHolder.getTenantId());
    }

    @TopicListener(destination = TOPIC_NAME, subscription = SUBSCRIPTION_NAME)
    public void receiveMessageTopic(User user) {
        LOGGER.info("Received message topic: {} tenant {}", user.getName(), TenantContextHolder.getTenantId());
    }

}
