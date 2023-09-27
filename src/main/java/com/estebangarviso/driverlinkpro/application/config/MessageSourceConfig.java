package com.estebangarviso.driverlinkpro.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@Configuration
public class MessageSourceConfig {

    @Value("${application.country.locale:en}")
    private String locale;

    @Bean
    public MessageSource messageSource(){
        var bundleMessageSource = new ReloadableResourceBundleMessageSource();
        bundleMessageSource.setBasename("classpath:messages/"+locale);
        bundleMessageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.name());
        bundleMessageSource.setUseCodeAsDefaultMessage(true);

        return bundleMessageSource;
    }
}

