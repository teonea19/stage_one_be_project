package stage_one.stage_one.controller;

import stage_one.stage_one.dto.ProfileListResponse;
import stage_one.stage_one.dto.ProfileResponse;
import stage_one.stage_one.entity.Profile;

public class ProfileMapper {

    public static ProfileResponse toResponse(Profile p) {
        return ProfileResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .gender(p.getGender())
                .gender_probability(p.getGenderProbability())
                .sample_size(p.getSampleSize())
                .age(p.getAge())
                .age_group(p.getAgeGroup())
                .country_id(p.getCountryId())
                .country_probability(p.getCountryProbability())
                .created_at(p.getCreatedAt().toString())
                .build();
    }

    public static ProfileListResponse toListResponse(Profile p) {
        return ProfileListResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .gender(p.getGender())
                .age(p.getAge())
                .age_group(p.getAgeGroup())
                .country_id(p.getCountryId())
                .build();
    }
}
