package hr.tvz.napredna.java.dijezetserver.service.impl;

import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import hr.tvz.napredna.java.dijezetserver.model.Station;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.repository.StationRepository;
import hr.tvz.napredna.java.dijezetserver.service.StationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    public List<StationDto> findAll() {
        return stationRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public StationDto create(StationDto stationDto, User user) {
        return toDto(stationRepository.save(toEntity(stationDto)));
    }

    @Override
    public void deleteById(Long id) {
        stationRepository.findById(id).orElseThrow(() -> ApiException.notFound("Station with id " + id + " not found"));
        stationRepository.deleteById(id);
    }

    @Override
    public StationDto update(Long id, StationDto stationDto) {
        Optional<Station> stationUpdatedOptional = stationRepository.findById(id);
        if (stationUpdatedOptional.isPresent()) {
            Station station = stationUpdatedOptional.get();
            station.setName(stationDto.getName());
            toDto(stationRepository.save(station));
            return toDto(station);
        } else {
            throw ApiException.notFound("Station with id " + id + " not found");
        }
    }

    private StationDto toDto(Station station) {
        return new StationDto(station.getId(), station.getName());
    }

    private Station toEntity(StationDto stationDto) {
        return new Station(stationDto.getName());
    }
}
