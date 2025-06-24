package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.service.StationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StationControllerTest extends BaseTest {
    static final List<StationDto> STATIONS_DTO = List.of(new StationDto(STATION.getId(), STATION.getName()));
    static final StationDto STATION_DTO = STATIONS_DTO.getFirst();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StationService stationService;

    @Test
    void shouldGetStations() throws Exception {
        when(stationService.findAll()).thenReturn(STATIONS_DTO);
        mockMvc.perform(get(ApiPaths.STATION))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(STATIONS_DTO.size()));
    }

    @Test
    void shouldCreateStation() throws Exception {
        when(stationService.save(any())).thenReturn(STATION_DTO);
        mockMvc.perform(post(ApiPaths.STATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(STATION_DTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(STATION_DTO.getName()));
    }

    @Test
    void shouldUpdateStation() throws Exception {
        when(stationService.update(anyLong(), any())).thenReturn(STATION_DTO);
        mockMvc.perform(put(ApiPaths.STATION + "/" + STATION_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(STATION_DTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(STATION_DTO.getName()));
    }

    @Test
    void shouldDeleteStation() throws Exception {
        doNothing().when(stationService).deleteById(anyLong());
        mockMvc.perform(delete(ApiPaths.STATION + "/" + STATION_DTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundOnDelete() throws Exception {
        doThrow(new EntityNotFoundException()).when(stationService).deleteById(anyLong());
        mockMvc.perform(delete(ApiPaths.STATION + "/" + STATION_DTO.getId()))
                .andExpect(status().isNotFound());
    }
}
