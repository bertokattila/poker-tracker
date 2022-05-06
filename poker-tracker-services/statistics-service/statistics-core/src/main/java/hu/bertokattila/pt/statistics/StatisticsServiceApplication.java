package hu.bertokattila.pt.statistics;

import hu.bertokattila.pt.statistics.config.ServiceUrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ServiceUrlProperties.class})
@SpringBootApplication
public class StatisticsServiceApplication {
  public static void main(String[] args){
    SpringApplication.run(StatisticsServiceApplication.class, args);
  }
}
