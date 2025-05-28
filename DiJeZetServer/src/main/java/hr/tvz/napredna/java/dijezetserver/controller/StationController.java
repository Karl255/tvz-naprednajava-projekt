package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.service.StationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/di-je-zet/api/station")
@AllArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public ResponseEntity<List<StationDto>> getStations() {
        return ResponseEntity.ok(stationService.findAll());
    }

    @PostMapping
    public ResponseEntity<StationDto> createStation(@RequestBody StationDto stationDto) {
        return new ResponseEntity<>(stationService.save(stationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StationDto> updateStation(@PathVariable BigInteger id, @RequestBody StationDto stationDto){
        return new ResponseEntity<>(stationService.update(id, stationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable BigInteger id){
        try{
            stationService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
