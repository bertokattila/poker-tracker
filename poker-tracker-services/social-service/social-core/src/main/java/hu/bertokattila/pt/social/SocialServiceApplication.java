package hu.bertokattila.pt.social;

import hu.bertokattila.pt.social.config.ServiceUrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ServiceUrlProperties.class})
@SpringBootApplication
public class SocialServiceApplication {
  public static void main(String[] args){
    SpringApplication.run(SocialServiceApplication.class, args);
  }
}
