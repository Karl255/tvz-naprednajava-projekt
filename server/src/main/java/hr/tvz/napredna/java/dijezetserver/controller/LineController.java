package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.service.LineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.LINE)
@Tag(name = "Line", description = "CRUD operations for public transport lines")
@AllArgsConstructor
public class LineController {

    private final LineService lineService;

    @GetMapping
    public ResponseEntity<List<LineDto>> getLines() {
        return ResponseEntity.ok(lineService.findAll());
    }

    @PostMapping
    public ResponseEntity<LineDto> addLine(@RequestBody LineDto lineDto) {
        return new ResponseEntity<>(lineService.save(lineDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineDto> updateLine(@PathVariable Long id, @RequestBody LineDto lineDto) {
        return new ResponseEntity<>(lineService.update(id, lineDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        try {
            lineService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
