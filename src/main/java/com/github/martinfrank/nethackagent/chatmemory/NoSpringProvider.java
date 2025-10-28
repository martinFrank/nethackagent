package com.github.martinfrank.nethackagent.chatmemory;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.github.martinfrank.nethackagent.chatmemory")
@ComponentScan(basePackages = "com.github.martinfrank.nethackagent")
@Configuration
@PropertySource("classpath:application.properties")
public class NoSpringProvider {
}

