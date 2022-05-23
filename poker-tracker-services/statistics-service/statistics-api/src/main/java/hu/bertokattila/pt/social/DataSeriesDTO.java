package hu.bertokattila.pt.social;

import lombok.Data;

import java.util.List;

@Data
public class DataSeriesDTO {
    private String name;
    private List<DataPointDTO> series;
}
