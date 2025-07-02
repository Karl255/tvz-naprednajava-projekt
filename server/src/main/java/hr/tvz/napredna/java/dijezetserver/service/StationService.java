package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.model.User;

import java.util.List;

public interface StationService extends EntityService<StationDto, StationDto> {
    @Override
    List<StationDto> findAll();

    @Override
    StationDto create(StationDto stationDto, User user);

    @Override
    StationDto update(Long id, StationDto stationDto);

    @Override
    void deleteById(Long id);
}
