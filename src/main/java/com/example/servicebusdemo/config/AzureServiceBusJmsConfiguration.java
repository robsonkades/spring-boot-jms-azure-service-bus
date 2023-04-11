package com.example.servicebusdemo.config;

import com.azure.spring.cloud.autoconfigure.implementation.jms.properties.AzureServiceBusJmsProperties;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


@Configuration
public class AzureServiceBusJmsConfiguration {

    private final AzureServiceBusJmsProperties azureServiceBusJMSProperties;

    public AzureServiceBusJmsConfiguration(AzureServiceBusJmsProperties azureServiceBusJMSProperties) {
        this.azureServiceBusJMSProperties = azureServiceBusJMSProperties;
    }

    @Bean("customJmsListenerContainerFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new AzureServiceBusJmsListenerContainerFactory();
        configurer.configure(jmsListenerContainerFactory, connectionFactory);
        jmsListenerContainerFactory.setPubSubDomain(Boolean.FALSE);
        configureCommonListenerContainerFactory(jmsListenerContainerFactory);
        return jmsListenerContainerFactory;
    }

    @Bean("customTopicJmsListenerContainerFactory")
    public JmsListenerContainerFactory<?> topicJmsListenerContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new AzureServiceBusJmsListenerContainerFactory();
        configurer.configure(jmsListenerContainerFactory, connectionFactory);
        jmsListenerContainerFactory.setPubSubDomain(Boolean.TRUE);
        configureCommonListenerContainerFactory(jmsListenerContainerFactory);
        configureTopicListenerContainerFactory(jmsListenerContainerFactory);
        return jmsListenerContainerFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        JmsTemplate azureJmsTemplate = new AzureJmsTemplate(connectionFactory);
        azureJmsTemplate.setMessageConverter(messageConverter);
        return azureJmsTemplate;
    }

    @Bean
    public MessageConverter converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter() {
            @Override
            public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
                TextMessage message = (TextMessage) super.toMessage(object, session);
                //System.out.println("outbound json: " + message.getText());
                return message;
            }

            @Override
            public Object fromMessage(Message message) throws JMSException, MessageConversionException {
                //System.out.println("inbound json: " + ((TextMessage) message).getText());
                return super.fromMessage(message);
            }

        };
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    private void configureCommonListenerContainerFactory(DefaultJmsListenerContainerFactory jmsListenerContainerFactory) {
        AzureServiceBusJmsProperties.Listener listener = azureServiceBusJMSProperties.getListener();
        if (listener.getReplyQosSettings() != null) {
            jmsListenerContainerFactory.setReplyQosSettings(listener.getReplyQosSettings());
        }
        if (listener.getPhase() != null) {
            jmsListenerContainerFactory.setPhase(listener.getPhase());
        }
    }

    private void configureTopicListenerContainerFactory(DefaultJmsListenerContainerFactory jmsListenerContainerFactory) {
        AzureServiceBusJmsProperties.Listener listener = azureServiceBusJMSProperties.getListener();
        if (listener.isReplyPubSubDomain() != null) {
            jmsListenerContainerFactory.setReplyPubSubDomain(listener.isReplyPubSubDomain());
        }
        if (listener.isSubscriptionDurable() != null) {
            jmsListenerContainerFactory.setSubscriptionDurable(listener.isSubscriptionDurable());
        }
        if (listener.isSubscriptionShared() != null) {
            jmsListenerContainerFactory.setSubscriptionShared(listener.isSubscriptionShared());
        }
    }

}
