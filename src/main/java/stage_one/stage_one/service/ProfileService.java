package stage_one.stage_one.service;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.stereotype.Service;
import stage_one.stage_one.client.ApiClient;
import stage_one.stage_one.controller.ProfileMapper;
import stage_one.stage_one.dto.AgifyResponse;
import stage_one.stage_one.dto.GenderizeResponse;
import stage_one.stage_one.dto.NationalizeResponse;
import stage_one.stage_one.entity.Profile;
import stage_one.stage_one.exception.ApiException;
import stage_one.stage_one.exception.UpstreamException;
import stage_one.stage_one.repository.ProfileRepository;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repo;
    private final ApiClient client;

    public Map<String, Object> createProfile(String name) throws BadRequestException {

        if (name == null || name.isBlank())
            throw new BadRequestException("Missing or empty name");

        var existing = repo.findByNameIgnoreCase(name);

        if (existing.isPresent()) {
            return Map.of(
                    "status", "success",
                    "message", "Profile already exists",
                    "data", ProfileMapper.toResponse(existing.get())
            );
        }

        var g = client.get("https://api.genderize.io?name=" + name, GenderizeResponse.class);
        var a = client.get("https://api.agify.io?name=" + name, AgifyResponse.class);
        var n = client.get("https://api.nationalize.io?name=" + name, NationalizeResponse.class);

        if (g.getGender() == null || g.getCount() == 0)
            throw new UpstreamException("Genderize returned an invalid response");

        if (a.getAge() == null)
            throw new UpstreamException("Agify returned an invalid response");

        if (n.getCountry() == null || n.getCountry().isEmpty())
            throw new UpstreamException("Nationalize returned an invalid response");

        String ageGroup = getAgeGroup(a.getAge());

        var bestCountry = n.getCountry().stream()
                .max(Comparator.comparing(NationalizeResponse.Country::getProbability))
                .get();

        Profile profile = Profile.builder()
                .id(UUID.randomUUID().toString()) // safe for MySQL
                .name(name.toLowerCase())
                .gender(g.getGender())
                .genderProbability(g.getProbability())
                .sampleSize(g.getCount())
                .age(a.getAge())
                .ageGroup(ageGroup)
                .countryId(bestCountry.getCountry_id())
                .countryProbability(bestCountry.getProbability())
                .createdAt(Instant.now())
                .build();

        repo.save(profile);

        return Map.of(
                "status", "success",
                "data", ProfileMapper.toResponse(profile)
        );
    }

    public Profile getProfile(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new ApiException("Profile not found"));
    }

    public List<Profile> getProfiles(String gender, String country, String ageGroup) {

        if (gender != null && country != null && ageGroup != null) {
            return repo.findByGenderIgnoreCaseAndCountryIdIgnoreCaseAndAgeGroupIgnoreCase(
                    gender, country, ageGroup
            );
        }

        return repo.findAll();
    }

    public void deleteProfile(UUID id) {
        if (!repo.existsById(id))
            throw new ApiException("Profile not found");

        repo.deleteById(id);
    }

    private String getAgeGroup(int age) {
        if (age <= 12) return "child";
        if (age <= 19) return "teenager";
        if (age <= 59) return "adult";
        return "senior";
    }
}
