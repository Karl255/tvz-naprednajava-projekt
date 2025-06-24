package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.model.Station;
import hr.tvz.napredna.java.dijezetserver.repository.StationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StationServiceTest extends BaseTest {
    static final StationDto STATION_DTO = new StationDto(STATION.getId(), STATION.getName());

    @Autowired
    private StationService stationService;

    @MockitoBean
    private StationRepository stationRepository;

    @Test
    void shouldGetStations() {
        when(stationRepository.findAll()).thenReturn(STATIONS);

        var stations = stationService.findAll();
        assertEquals(STATIONS.size(), stations.size());
    }

    @Test
    void shouldSaveStation() {
        when(stationRepository.save(any())).thenReturn(STATION);
        ArgumentCaptor<Station> stationCaptor = ArgumentCaptor.forClass(Station.class);

        stationService.save(STATION_DTO);

        verify(stationRepository, times(1)).save(stationCaptor.capture());
        var station = stationCaptor.getValue();
        assertEquals(STATION_DTO.getName(), station.getName());
    }

    @Test
    void shouldUpdateStation() {
        when(stationRepository.findById(any())).thenReturn(Optional.of(STATION));
        when(stationRepository.save(any())).thenReturn(STATION);
        ArgumentCaptor<Station> stationCaptor = ArgumentCaptor.forClass(Station.class);

        stationService.update(STATION.getId(), STATION_DTO);

        verify(stationRepository, times(1)).save(stationCaptor.capture());
        var station = stationCaptor.getValue();
        assertEquals(STATION.getId(), station.getId());
        assertEquals(STATION_DTO.getName(), station.getName());
    }

    @Test
    void shouldThrowErrorIfStationNotFoundOnUpdate() {
        when(stationRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> stationService.update(STATION.getId(), any()), "Sation with id " + STATION.getId() + " not found");
    }

    @Test
    void shouldDeleteStation() {
        when(stationRepository.findById(any())).thenReturn(Optional.of(STATION));
        doNothing().when(stationRepository).deleteById(anyLong());

        stationService.deleteById(STATION.getId());
        verify(stationRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void shouldThrowErrorIfStationNotFoundOnDelete() {
        when(stationRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> stationService.deleteById(STATION.getId()), "Sation with id " + STATION.getId() + " not found");
    }
}
