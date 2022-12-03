package hu.bertokattila.pt.session.controllers;

import hu.bertokattila.pt.session.CurrencyExchangeDTO;
import hu.bertokattila.pt.session.service.CurrencyExchangeService;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/currency")
public class CurrencyExchangeController {
  CurrencyExchangeService currencyExchangeService;

  public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService) {
    this.currencyExchangeService = currencyExchangeService;
  }

  @GetMapping("/exchange")
  public ResponseEntity<CurrencyExchangeDTO> exhange(@Valid @NotEmpty @RequestParam String from,
                                                     @Valid @NotEmpty @RequestParam String to,
                                                     @Valid @PositiveOrZero @RequestParam Double amount,
                                                     @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date) {

    Double[] result = currencyExchangeService.exchangeCurrency(from, to, amount, date);
    CurrencyExchangeDTO currencyExchangeDTO = new CurrencyExchangeDTO();
    currencyExchangeDTO.setResult(result[0]);
    currencyExchangeDTO.setRate(result[1]);
    return new ResponseEntity<>(currencyExchangeDTO, HttpStatus.OK);
  }
}
