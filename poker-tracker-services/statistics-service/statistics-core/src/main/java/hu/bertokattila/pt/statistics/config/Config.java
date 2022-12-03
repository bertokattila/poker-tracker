package hu.bertokattila.pt.statistics.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = "hu.bertokattila.pt.auth.filters")
@ComponentScan(basePackages = "hu.bertokattila.pt.auth.util")
@EnableScheduling
public class Config {
}
