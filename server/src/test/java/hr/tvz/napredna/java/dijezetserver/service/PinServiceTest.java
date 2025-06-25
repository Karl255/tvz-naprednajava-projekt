package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import hr.tvz.napredna.java.dijezetserver.model.Pin;
import hr.tvz.napredna.java.dijezetserver.repository.LineRepository;
import hr.tvz.napredna.java.dijezetserver.repository.PinRepository;
import hr.tvz.napredna.java.dijezetserver.repository.StationRepository;
import hr.tvz.napredna.java.dijezetserver.request.PinRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PinServiceTest extends BaseTest {
    static final PinRequest PIN_REQUEST = new PinRequest(STATION.getId(), LINE.getId(), PIN.getLatitude(), PIN.getLongitude());

    @Autowired
    private PinService pinService;

    @MockitoBean
    private PinRepository pinRepository;
    @MockitoBean
    private StationRepository stationRepository;
    @MockitoBean
    private LineRepository lineRepository;

    @Test
    void shouldGetPins() {
        when(pinRepository.findAll()).thenReturn(PINS);

        var pins = pinService.findAll();
        assertEquals(1, pins.size());
    }

    @Test
    void shouldSavePin() {
        when(stationRepository.findById(anyLong())).thenReturn(Optional.of(STATION));
        when(lineRepository.findById(anyLong())).thenReturn(Optional.of(LINE));
        when(pinRepository.save(any())).thenReturn(PIN);
        ArgumentCaptor<Pin> pinCaptor = ArgumentCaptor.forClass(Pin.class);

        pinService.save(PIN_REQUEST, USER);

        verify(pinRepository, times(1)).save(pinCaptor.capture());
        var pin = pinCaptor.getValue();
        assertEquals(pin.getLongitude(), PIN_REQUEST.getLongitude());
        assertEquals(pin.getLatitude(), PIN_REQUEST.getLatitude());
        assertEquals(pin.getStation().getId(), STATION.getId());
        assertEquals(pin.getLine().getId(), LINE.getId());
    }

    @Test
    void shouldUpdatePin() {
        when(stationRepository.findById(anyLong())).thenReturn(Optional.of(STATION));
        when(lineRepository.findById(anyLong())).thenReturn(Optional.of(LINE));
        when(pinRepository.findById(anyLong())).thenReturn(Optional.of(PIN));
        when(pinRepository.save(any())).thenReturn(PIN);
        ArgumentCaptor<Pin> pinCaptor = ArgumentCaptor.forClass(Pin.class);

        pinService.update(PIN.getId(), PIN_REQUEST);

        verify(pinRepository, times(1)).save(pinCaptor.capture());
        var pin = pinCaptor.getValue();
        assertEquals(pin.getLongitude(), PIN_REQUEST.getLongitude());
        assertEquals(pin.getLatitude(), PIN_REQUEST.getLatitude());
        assertEquals(pin.getStation().getId(), PIN_REQUEST.getStationId());
        assertEquals(pin.getLine().getId(), PIN_REQUEST.getLineId());
    }

    @Test
    void shouldThrowExceptionIfPinNotFoundOnUpdate() {
        when(pinRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> pinService.update(PIN.getId(), any()), "Pin does not exist");
    }

    @Test
    void shouldDeletePin() {
        when(pinRepository.findById(anyLong())).thenReturn(Optional.of(PIN));
        doNothing().when(pinRepository).delete(any());

        pinService.deleteById(PIN.getId());
        verify(pinRepository, times(1)).delete(any());
    }

    @Test
    void shouldThrowExceptionIfPinNotFoundOnDelete() {
        when(pinRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> pinService.deleteById(anyLong()), "Pin does not exist");
    }

    @Test
    void shouldDeleteMultiplePins() {
        var ids = List.of(1L, 2L, 3L);

        pinService.deleteByIds(ids);

        verify(pinRepository, times(1)).deleteAllById(ids);
    }

    @Test
    void shouldSavePinWithoutStationAndLine() {
        var request = new PinRequest(null, null, new BigDecimal("45.0"), new BigDecimal("16.0"));
        when(pinRepository.save(any())).thenReturn(PIN);

        pinService.save(request, USER);

        verify(pinRepository).save(any());
    }

    @Test
    void shouldThrowIfStationDoesNotExist() {
        when(stationRepository.findById(anyLong())).thenReturn(Optional.empty());

        var ex = assertThrows(ApiException.class, () -> {
            pinService.save(PIN_REQUEST, USER);
        });

        assertEquals(List.of("Station does not exist"), ex.getMessages());
    }

    @Test
    void shouldThrowIfLineDoesNotExistOnUpdate() {
        when(pinRepository.findById(anyLong())).thenReturn(Optional.of(PIN));
        when(stationRepository.findById(anyLong())).thenReturn(Optional.of(STATION));
        when(lineRepository.findById(anyLong())).thenReturn(Optional.empty());

        var ex = assertThrows(ApiException.class, () -> {
            pinService.update(PIN.getId(), PIN_REQUEST);
        });

        assertEquals(List.of("Line does not exist"), ex.getMessages());
    }
}