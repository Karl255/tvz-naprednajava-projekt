package hr.tvz.napredna.java.dijezetserver.service.impl;

import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.dto.PinDto;
import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.mapper.LineMapper;
import hr.tvz.napredna.java.dijezetserver.mapper.PinMapper;
import hr.tvz.napredna.java.dijezetserver.mapper.StationMapper;
import hr.tvz.napredna.java.dijezetserver.model.Line;
import hr.tvz.napredna.java.dijezetserver.model.Pin;
import hr.tvz.napredna.java.dijezetserver.model.Station;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.repository.LineRepository;
import hr.tvz.napredna.java.dijezetserver.repository.PinRepository;
import hr.tvz.napredna.java.dijezetserver.repository.StationRepository;
import hr.tvz.napredna.java.dijezetserver.request.PinRequest;
import hr.tvz.napredna.java.dijezetserver.service.PinService;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PinServiceImpl implements PinService {

    private final PinRepository pinRepository;
    private final StationRepository stationRepository;
    private final LineRepository lineRepository;

    @Override
    public List<PinDto> findAll() {
        return pinRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public PinDto save(PinRequest pinRequest, User user) {
        Station station = resolveOptionalStation(pinRequest.getStationId()).orElse(null);
        Line line = resolveOptionalLine(pinRequest.getLineId()).orElse(null);

        return toDto(pinRepository.save(toEntity(pinRequest, station, line, user)));
    }

    @Override
    public PinDto update(Long id, PinRequest pinRequest) {
        Pin existingPin = pinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pin does not exist"));

        Station station = resolveOptionalStation(pinRequest.getStationId()).orElse(null);
        Line line = resolveOptionalLine(pinRequest.getLineId()).orElse(null);

        existingPin.setStation(station);
        existingPin.setLine(line);
        existingPin.setLatitude(pinRequest.getLatitude());
        existingPin.setLongitude(pinRequest.getLongitude());

        return toDto(pinRepository.save(existingPin));
    }

    @Override
    public void deleteById(Long id) {
        Pin existingPin = pinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pin does not exist"));

        pinRepository.delete(existingPin);
    }

    private Optional<Station> resolveOptionalStation(@Nullable Long stationId) {
        if (stationId == null) {
            return Optional.empty();
        }

        return Optional.of(stationRepository.findById(stationId).orElseThrow(() -> new EntityNotFoundException("Station does not exist")));
    }

    private Optional<Line> resolveOptionalLine(@Nullable Long lineId) {
        if (lineId == null) {
            return Optional.empty();
        }

        return Optional.of(lineRepository.findById(lineId).orElseThrow(() -> new EntityNotFoundException("Line does not exist")));
    }

    private PinDto toDto(Pin pin) {
        StationDto stationDto = Optional.ofNullable(pin.getStation()).map(StationMapper::toDto).orElse(null);
        LineDto lineDto = Optional.ofNullable(pin.getLine()).map(LineMapper::toDto).orElse(null);
        return PinMapper.toDto(pin, stationDto, lineDto);
    }

    private Pin toEntity(PinRequest pinRequest, Station station, Line line, User user) {
        return PinMapper.toEntity(pinRequest, station, line, user);
    }
}
