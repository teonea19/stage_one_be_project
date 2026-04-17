package stage_one.stage_one.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;
import stage_one.stage_one.service.ProfileService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProfileController {

    private final ProfileService service;

    @PostMapping
    public Object create(@RequestBody Map<String, String> body) throws BadRequestException {
        return service.createProfile(body.get("name"));
    }

    @GetMapping("/{id}")
    public Object getOne(@PathVariable UUID id) {
        return Map.of("status", "success", "data", service.getProfile(id));
    }

    @GetMapping
    public Object getAll(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String country_id,
            @RequestParam(required = false) String age_group
    ) {
        var list = service.getProfiles(gender, country_id, age_group);

        return Map.of(
                "status", "success",
                "count", list.size(),
                "data", list
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteProfile(id);
    }
}
