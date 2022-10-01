package hu.bertokattila.pt.statistics.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSessionRepostConsumer {

  @KafkaListener(topics = "sessionReport", groupId = "sessionReportConsumer")
  public void consume(String message) {
    System.out.println("message = " + message);
  }
}
