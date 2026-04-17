package stage_one.stage_one.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "profiles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Profile {

    @Id
    private String id; // store UUID as String

    @Column(nullable = false, unique = true)
    private String name;

    private String gender;

    @Column(name = "gender_probability")
    private Double genderProbability;

    @Column(name = "sample_size")
    private Integer sampleSize;

    private Integer age;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "country_probability")
    private Double countryProbability;

    @Column(name = "created_at")
    private Instant createdAt;
}