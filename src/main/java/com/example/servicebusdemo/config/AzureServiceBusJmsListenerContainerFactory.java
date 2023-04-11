package com.example.servicebusdemo.config;

import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class AzureServiceBusJmsListenerContainerFactory extends DefaultJmsListenerContainerFactory {

    @Override
    protected DefaultMessageListenerContainer createContainerInstance() {
        return new CustomMessageListenerContainer();
    }
}
