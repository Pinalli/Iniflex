package com.pinalli.iniflex.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public ExcelService excelService() {
        return new ExcelService();
    }
}