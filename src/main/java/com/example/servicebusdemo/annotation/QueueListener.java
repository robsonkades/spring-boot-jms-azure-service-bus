package com.example.servicebusdemo.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MessageMapping
@JmsListener(destination = "")
public @interface QueueListener {

    /**
     * The unique identifier of the container managing this endpoint.
     * <p>If none is specified, an auto-generated one is provided.
     * @see org.springframework.jms.config.JmsListenerEndpointRegistry#getListenerContainer(String)
     */

    @AliasFor(
            annotation = JmsListener.class
    )
    String id() default "";

    /**
     * The bean name of the {@link org.springframework.jms.config.JmsListenerContainerFactory}
     * to use to create the message listener container responsible for serving this endpoint.
     * <p>If not specified, the default container factory is used, if any.
     */
    @AliasFor(
            annotation = JmsListener.class
    )
    String containerFactory() default "customJmsListenerContainerFactory";

    /**
     * The destination name for this listener, resolved through the container-wide
     * {@link org.springframework.jms.support.destination.DestinationResolver} strategy.
     */
    @AliasFor(
            annotation = JmsListener.class
    )
    String destination();

    /**
     * The JMS message selector expression, if any.
     * <p>See the JMS specification for a detailed definition of selector expressions.
     */

    @AliasFor(
            annotation = JmsListener.class
    )
    String selector() default "";

    /**
     * The concurrency limits for the listener, if any. Overrides the value defined
     * by the container factory used to create the listener container.
     * <p>The concurrency limits can be a "lower-upper" String &mdash; for example,
     * "5-10" &mdash; or a simple upper limit String &mdash; for example, "10", in
     * which case the lower limit will be 1.
     * <p>Note that the underlying container may or may not support all features.
     * For instance, it may not be able to scale, in which case only the upper limit
     * is used.
     */

    @AliasFor(
            annotation = JmsListener.class
    )
    String concurrency() default "";

}
