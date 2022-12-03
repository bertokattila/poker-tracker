package hu.bertokattila.pt.social;

import java.util.List;
import lombok.Data;

@Data
public class DataSeriesDTO {
  private String name;
  private List<DataPointDTO> series;
}
