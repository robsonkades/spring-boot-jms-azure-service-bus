package com.example.servicebusdemo.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageProducer;
import org.springframework.jms.core.JmsTemplate;

import java.io.Serializable;

public class AzureJmsTemplate extends JmsTemplate implements Serializable {

    public AzureJmsTemplate() {
    }

    public AzureJmsTemplate(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected void doSend(MessageProducer producer, Message message) throws JMSException {
        if (getDeliveryDelay() >= 0) {
            producer.setDeliveryDelay(getDeliveryDelay());
        }
        producer.send(message, getDeliveryMode(), message.getJMSPriority(), getTimeToLive());
    }
}
