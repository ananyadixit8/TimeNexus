package com.TimeNexus.TimeNexus.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for the application.
 */
@Configuration
public class TimeNexusConfig {

    /**
     * Initialize the model mapper object.
     * @return New ModelMapper object.
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
