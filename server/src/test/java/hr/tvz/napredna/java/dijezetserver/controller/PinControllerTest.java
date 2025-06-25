package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.PinDto;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import hr.tvz.napredna.java.dijezetserver.service.PinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PinControllerTest extends BaseTest {
    static final List<PinDto> PIN_DTOS = List.of(new PinDto(PIN.getId(), null, null, PIN.getLatitude(), PIN.getLongitude(), null));
    static final PinDto PIN_DTO = PIN_DTOS.getFirst();
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PinService pinService;

    @Test
    void shouldGetPins() throws Exception {
        when(pinService.findAll()).thenReturn(PIN_DTOS);
        mockMvc.perform(get(ApiPaths.PIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(PIN_DTOS.size()));
    }

    @Test
    void shouldCreatePin() throws Exception {
        when(pinService.save(any(), any())).thenReturn(PIN_DTO);
        mockMvc.perform(post(ApiPaths.PIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(PIN_DTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.latitude").value(PIN.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(PIN.getLongitude()));
    }

    @Test
    void shouldReturnBadRequestOnCreatePin() throws Exception {
        doThrow(ApiException.badRequest()).when(pinService).save(any(), any());
        mockMvc.perform(post(ApiPaths.PIN).contentType(MediaType.APPLICATION_JSON).content(toJson(PIN_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdatePin() throws Exception {
        when(pinService.update(anyLong(), any())).thenReturn(PIN_DTO);
        mockMvc.perform(put(ApiPaths.PIN + "/" + PIN_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(PIN_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(PIN.getId()))
                .andExpect(jsonPath("$.latitude").value(PIN.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(PIN.getLongitude()));
    }

    @Test
    void shouldReturnBadRequestOnUpdatePin() throws Exception {
        doThrow(ApiException.badRequest()).when(pinService).update(anyLong(), any());
        mockMvc.perform(put(ApiPaths.PIN + "/" + PIN_DTO.getId()).contentType(MediaType.APPLICATION_JSON).content(toJson(PIN_DTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldDeletePin() throws Exception {
        doNothing().when(pinService).deleteById(anyLong());
        mockMvc.perform(delete(ApiPaths.PIN + "/" + PIN_DTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestOnDeletePin() throws Exception {
        doThrow(ApiException.notFound()).when(pinService).deleteById(anyLong());
        mockMvc.perform(delete(ApiPaths.PIN + "/" + PIN_DTO.getId()))
                .andExpect(status().isNotFound());
    }
}
