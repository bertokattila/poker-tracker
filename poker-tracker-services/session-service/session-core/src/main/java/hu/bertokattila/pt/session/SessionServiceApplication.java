package hu.bertokattila.pt.session;

import hu.bertokattila.pt.session.config.ServiceUrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ServiceUrlProperties.class})
@SpringBootApplication
public class SessionServiceApplication {
  public static void main(String[] args){
    SpringApplication.run(SessionServiceApplication.class, args);
  }
}
