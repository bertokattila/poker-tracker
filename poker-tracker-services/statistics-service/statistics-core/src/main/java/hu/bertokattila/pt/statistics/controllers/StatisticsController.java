package hu.bertokattila.pt.statistics.controllers;

import hu.bertokattila.pt.statistics.model.GenericStatisticsRec;
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

  /**
   * Other services can call this
   * Refreshes the statistics for the user
   * @param userID
   * @return
   */
  @PostMapping("/refresh/{userID}")
  @Valid
  public ResponseEntity<?> addSession(@Valid @PositiveOrZero @PathVariable int userID){
    statisticsService.refreshStatistics(userID);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  @GetMapping("/generic")
  @Valid
  public ResponseEntity<GenericStatisticsRec> getGeneric(){
    GenericStatisticsRec res = statisticsService.getGenericStatistics();
    if(res == null){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

}
