package com.example.servicebusdemo.config;

import com.example.servicebusdemo.context.TenantContextHolder;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class CustomMessageListenerContainer extends DefaultMessageListenerContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMessageListenerContainer.class);

    @Override
    protected void doExecuteListener(Session session, Message message) throws JMSException {
        try {
            String tenant = message.getStringProperty("tenant");
            LOGGER.info("priority {}", message.getJMSPriority());
            TenantContextHolder.setTenantId(tenant);
            super.doExecuteListener(session, message);
        } finally {
            TenantContextHolder.clear();
        }
    }
}
