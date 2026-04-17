package stage_one.stage_one.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stage_one.stage_one.entity.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByNameIgnoreCase(String name);

    List<Profile> findByGenderIgnoreCaseAndCountryIdIgnoreCaseAndAgeGroupIgnoreCase(
            String gender, String countryId, String ageGroup
    );

}
