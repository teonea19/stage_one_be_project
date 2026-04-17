package stage_one.stage_one.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    private String id;
    private String name;
    private String gender;
    private Double gender_probability;
    private Integer sample_size;
    private Integer age;
    private String age_group;
    private String country_id;
    private Double country_probability;
    private String created_at;
}
