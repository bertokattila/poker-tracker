package hu.bertokattila.pt.statistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledService {
  GenericStatisticsService genericStatisticsService;

  @Autowired
  public ScheduledService(GenericStatisticsService genericStatisticsService) {
    this.genericStatisticsService = genericStatisticsService;
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void scheduleTaskUsingCronExpression() {
    genericStatisticsService.midnightJob();
  }

}
