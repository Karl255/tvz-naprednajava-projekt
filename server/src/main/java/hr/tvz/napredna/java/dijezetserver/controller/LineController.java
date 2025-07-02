package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.service.LineService;
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
@RequestMapping(ApiPaths.LINE)
@Tag(name = "Line", description = "CRUD operations for public transport lines")
@AllArgsConstructor
public class LineController {

    private final LineService lineService;

    @GetMapping
    public List<LineDto> getLines() {
        return lineService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LineDto addLine(@RequestBody LineDto lineDto, @AuthenticationPrincipal User user) {
        return lineService.create(lineDto, user);
    }

    @PutMapping("/{id}")
    public LineDto updateLine(@PathVariable Long id, @RequestBody LineDto lineDto) {
        return lineService.update(id, lineDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLine(@PathVariable Long id) {
        lineService.deleteById(id);
    }

}
