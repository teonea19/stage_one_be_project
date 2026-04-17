package stage_one.stage_one.dto;

import lombok.Data;

@Data
public class GenderizeResponse {
    private String gender;
    private Double probability;
    private Integer count;
}
