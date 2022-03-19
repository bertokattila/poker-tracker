package hu.bertokattila.pt.session;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SessionDTO {
  @NotBlank
  private String type;
  @NotBlank
  private String currency;
  @Positive
  private double buyIn;
  @PositiveOrZero
  private double cashOut;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime startDate;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime endDate;
  private String comment;

  private int locationId;
}
