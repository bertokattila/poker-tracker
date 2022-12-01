package hu.bertokattila.pt.statistics.kafka;

import hu.bertokattila.pt.session.ExtendedSessionDTO;
import hu.bertokattila.pt.session.SessionRemovedDTO;
import hu.bertokattila.pt.statistics.service.GenericStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSessionRemovedReportConsumer {
  @Autowired
  private GenericStatisticsService genericStatisticsService;

  @KafkaListener(topics = "sessionRemovedReport", groupId = "sessionReportConsumer")
  public void consume(SessionRemovedDTO dto) {
    genericStatisticsService.sessionDeleted(dto);
  }
}
