package hu.bertokattila.pt.statistics.kafka;

import hu.bertokattila.pt.session.ExtendedSessionDTO;
import hu.bertokattila.pt.statistics.service.GenericStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSessionReportConsumer {
  @Autowired
  private GenericStatisticsService genericStatisticsService;

  @KafkaListener(topics = "sessionReport", groupId = "sessionReportConsumer")
  public void consume(ExtendedSessionDTO sessionDTO) {
    genericStatisticsService.refreshStatistics(sessionDTO);
  }
}
