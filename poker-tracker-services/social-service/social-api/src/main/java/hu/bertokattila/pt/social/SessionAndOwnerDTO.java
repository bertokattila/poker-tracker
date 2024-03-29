package hu.bertokattila.pt.social;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class SessionAndOwnerDTO {
  @NotBlank
  private String ownerEmail;
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
  private String location;
  @NotBlank
  private String access;
  private Double ante;
  @NotBlank
  private String specificGameType;
  private String blinds;
  private Integer tableSize;
}
