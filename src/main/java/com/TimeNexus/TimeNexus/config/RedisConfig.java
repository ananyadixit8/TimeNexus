package com.TimeNexus.TimeNexus.config;

import com.TimeNexus.TimeNexus.service.MeetingExpirationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConfig {

    @Autowired
    private MeetingExpirationListener meetingExpirationListener;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        // Subscribe to the correct Redis channel for key expiration events
        container.addMessageListener(meetingExpirationListener, new PatternTopic("__keyevent@0__:expired"));

        return container;
    }
}
