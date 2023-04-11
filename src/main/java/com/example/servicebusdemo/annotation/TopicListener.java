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
public @interface TopicListener {

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
    String containerFactory() default "customTopicJmsListenerContainerFactory";

    /**
     * The destination name for this listener, resolved through the container-wide
     * {@link org.springframework.jms.support.destination.DestinationResolver} strategy.
     */
    @AliasFor(
            annotation = JmsListener.class
    )
    String destination();

    /**
     * The name for the durable subscription, if any.
     * <p>As of Spring Framework 5.3.26, if an explicit subscription name is not
     * specified, a default subscription name will be generated based on the fully
     * qualified name of the annotated listener method &mdash; for example,
     * {@code "org.example.jms.ProductListener.processRequest"} for a
     * {@code processRequest(...)} listener method in the
     * {@code org.example.jms.ProductListener} class.
     */

    @AliasFor(
            annotation = JmsListener.class
    )
    String subscription() default "";

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
