package hu.bertokattila.pt.statistics.controllers;

import hu.bertokattila.pt.statistics.service.GenericStatisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/statistics")
public class StatisticsController {
  private final GenericStatisticsService statisticsService;
  @Autowired
  public StatisticsController(GenericStatisticsService statisticsService){
    this.statisticsService = statisticsService;
  }

  @PostMapping("/refresh/{userID}")
  @Valid
  public ResponseEntity<?> addSession(@Valid @PositiveOrZero @PathVariable int userID){
    statisticsService.refreshStatistics(userID);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
