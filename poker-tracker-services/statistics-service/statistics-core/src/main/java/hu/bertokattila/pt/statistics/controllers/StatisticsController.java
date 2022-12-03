package hu.bertokattila.pt.statistics.controllers;

import hu.bertokattila.pt.social.DataSeriesDTO;
import hu.bertokattila.pt.statistics.model.GenericStatisticsRec;
import hu.bertokattila.pt.statistics.service.GenericStatisticsService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/statistics")
public class StatisticsController {
  private final GenericStatisticsService statisticsService;

  @Autowired
  public StatisticsController(GenericStatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @GetMapping("/generic")
  @Valid
  public ResponseEntity<GenericStatisticsRec> getGeneric() {
    GenericStatisticsRec res = statisticsService.getGenericStatistics();
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/result/yearly")
  @Valid
  public ResponseEntity<List<DataSeriesDTO>> getYearlyResult() {
    List<DataSeriesDTO> res = statisticsService.getYearlyResult();
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/result/monthly")
  @Valid
  public ResponseEntity<List<DataSeriesDTO>> getMonthlyResult() {
    List<DataSeriesDTO> res = statisticsService.getMonthlyResult();
    if (res == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

}
