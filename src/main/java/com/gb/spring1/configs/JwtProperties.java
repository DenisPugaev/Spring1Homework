package com.gb.spring1.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(JwtProperties.PREFIX)
public class JwtProperties {

    public static final String PREFIX = "jwt";

    private String secret;

    private Duration expireTime;

}
