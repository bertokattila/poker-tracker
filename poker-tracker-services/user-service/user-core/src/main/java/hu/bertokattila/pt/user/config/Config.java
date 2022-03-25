package hu.bertokattila.pt.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "hu.bertokattila.pt.auth.filters")
@ComponentScan(basePackages = "hu.bertokattila.pt.auth.util")
public class Config {
}
