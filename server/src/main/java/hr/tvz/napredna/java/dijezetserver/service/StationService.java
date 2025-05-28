package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.StationDto;

import java.util.List;

public interface StationService {
    List<StationDto> findAll();

    StationDto save(StationDto stationDto);

    StationDto update(Long id, StationDto stationDto);

    void deleteById(Long id);
}
