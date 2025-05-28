package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.StationDto;

import java.math.BigInteger;
import java.util.List;

public interface StationService {
    List<StationDto> findAll();
    StationDto save(StationDto stationDto);
    StationDto update(BigInteger id, StationDto stationDto);
    void deleteById(BigInteger id);
}
