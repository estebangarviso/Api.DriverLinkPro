package com.estebangarviso.driverlinkpro.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Configuration
public class EnvironmentConfig implements EnvironmentAware {

        Logger logger = LoggerFactory.getLogger(EnvironmentConfig.class);
        @Override
        public void setEnvironment(Environment environment) {
            var uriComponents = ServletUriComponentsBuilder.fromCurrentContextPath().build();
            var uriString = uriComponents.toUriString();
            logger.info("Active Environment: " + environment.getProperty("spring.profiles.active"));
            logger.info("Server Port: " + environment.getProperty("server.port"));
            logger.info("Server Host: " + environment.getProperty("server.host"));
            logger.info("Server URI: " + uriString);
        }
}
