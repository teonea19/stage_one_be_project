package stage_one.stage_one.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileListResponse {
    private String id;
    private String name;
    private String gender;
    private Integer age;
    private String age_group;
    private String country_id;
}
