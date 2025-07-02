package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.PinDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.request.PinRequest;
import hr.tvz.napredna.java.dijezetserver.service.PinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PIN)
@Tag(name = "Pin", description = "CRUD operations for pins")
@AllArgsConstructor
public class PinController {

    private final PinService pinService;

    @GetMapping
    public List<PinDto> getPins() {
        return pinService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PinDto createPin(@RequestBody PinRequest pinRequest, @AuthenticationPrincipal User user) {
        return pinService.create(pinRequest, user);
    }

    @PutMapping("/{id}")
    public PinDto updatePin(@PathVariable Long id, @RequestBody PinRequest pinRequest) {
        return pinService.update(id, pinRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePin(@PathVariable Long id) {
        pinService.deleteById(id);
    }

}
