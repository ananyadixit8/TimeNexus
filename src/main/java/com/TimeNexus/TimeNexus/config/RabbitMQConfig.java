package com.TimeNexus.TimeNexus.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;

@Configuration
@EnableRabbit
public class RabbitMQConfig implements RabbitListenerConfigurer {

    @Bean
    public ConnectionFactory connectionFactory() {
        //TODO: fetch from properties file
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost"); // RabbitMQ server host
        connectionFactory.setPort(5672); // RabbitMQ server port
        connectionFactory.setUsername("guest"); // RabbitMQ username
        connectionFactory.setPassword("guest"); // RabbitMQ password
        // Other connection properties can be configured here
        return connectionFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setContainerFactory(containerFactory());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // Set acknowledgment mode to MANUAL
        return factory;
    }

    @Bean
    public Queue myQueue() {
        return new Queue("meetingsQueue");
    }

    @Bean
    public Queue executionQueue() {
        return new Queue("executionQueue");
    }

    // Other configuration beans...

}
