package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.service.StationService;
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
@RequestMapping(ApiPaths.STATION)
@Tag(name = "Station", description = "CRUD operations for public transport stations")
@AllArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public List<StationDto> getStations() {
        return stationService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StationDto createStation(@RequestBody StationDto stationDto, @AuthenticationPrincipal User user) {
        return stationService.create(stationDto, user);
    }

    @PutMapping("/{id}")
    public StationDto updateStation(@PathVariable Long id, @RequestBody StationDto stationDto) {
        return stationService.update(id, stationDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStation(@PathVariable Long id) {
        stationService.deleteById(id);
    }
}
