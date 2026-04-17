package stage_one.stage_one.dto;

import lombok.Data;

import java.util.List;

@Data
public class NationalizeResponse {
    private List<Country> country;

    @Data
    public static class Country {
        private String country_id;
        private Double probability;
    }
}
