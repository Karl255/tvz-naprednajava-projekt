package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.service.LineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/di-je-zet/api/line")
@AllArgsConstructor
public class LineController {

    private final LineService lineService;

    @GetMapping
    public ResponseEntity<List<LineDto>> getLines(){
        return ResponseEntity.ok(lineService.findAll());
    }

    @PostMapping
    public ResponseEntity<LineDto> addLine(@RequestBody LineDto lineDto){
        return new ResponseEntity<>(lineService.save(lineDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineDto> updateLine(@PathVariable BigInteger id, @RequestBody LineDto lineDto){
        return new ResponseEntity<>(lineService.update(id, lineDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable BigInteger id) {
        try {
            lineService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
