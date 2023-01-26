package com.gb.spring1.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;


@ComponentScan
@Configuration
@PropertySource("secrets.properties")
public class AppConfig {
}
