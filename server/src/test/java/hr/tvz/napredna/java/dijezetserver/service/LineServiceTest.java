package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import hr.tvz.napredna.java.dijezetserver.model.Line;
import hr.tvz.napredna.java.dijezetserver.repository.LineRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LineServiceTest extends BaseTest {
    static final LineDto LINE_DTO = new LineDto(LINE.getId(), LINE.getName());

    @Autowired
    private LineService lineService;

    @MockitoBean
    private LineRepository lineRepository;

    @Test
    void shouldGetLines() {
        when(lineRepository.findAll()).thenReturn(LINES);

        var lines = lineService.findAll();
        assertEquals(3, lines.size());
    }

    @Test
    void shouldSaveLine() {
        when(lineRepository.save(any())).thenReturn(LINE);
        ArgumentCaptor<Line> lineCaptor = ArgumentCaptor.forClass(Line.class);

        lineService.create(LINE_DTO, USER);

        verify(lineRepository, times(1)).save(lineCaptor.capture());
        var line = lineCaptor.getValue();
        assertEquals(LINE_DTO.getName(), line.getName());
    }

    @Test
    void shouldUpdateLine() {
        when(lineRepository.save(any())).thenReturn(LINE);
        when(lineRepository.findById(anyLong())).thenReturn(Optional.of(LINE));
        ArgumentCaptor<Line> lineCaptor = ArgumentCaptor.forClass(Line.class);

        lineService.update(LINE.getId(), LINE_DTO);

        verify(lineRepository, times(1)).save(lineCaptor.capture());
        var line = lineCaptor.getValue();
        assertEquals(LINE.getId(), line.getId());
        assertEquals(LINE_DTO.getName(), line.getName());
    }

    @Test
    void shouldThrowExceptionIfLineNotFoundOnUpdate() {
        when(lineRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> lineService.update(LINE.getId(), any()), "Line with id " + LINE.getId() + " not found");
    }

    @Test
    void shouldDeleteLine() {
        when(lineRepository.findById(anyLong())).thenReturn(Optional.of(LINE));
        doNothing().when(lineRepository).deleteById(anyLong());

        lineService.deleteById(LINE.getId());
        verify(lineRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void shouldThrowExceptionIfLineNotFoundOnDelete() {
        when(lineRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> lineService.deleteById(anyLong()), "Line with id " + LINE.getId() + " not found");
    }
}
