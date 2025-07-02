package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.exceptions.ApiException;
import hr.tvz.napredna.java.dijezetserver.service.LineService;
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

public class LineControllerTest extends BaseTest {
    static final List<LineDto> LINES_DTO = List.of(new LineDto(LINE.getId(), LINE.getName()));
    static final LineDto LINE_DTO = LINES_DTO.getFirst();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LineService lineService;

    @Test
    void shouldGetLines() throws Exception {
        when(lineService.findAll()).thenReturn(LINES_DTO);
        mockMvc.perform(get(ApiPaths.LINE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(LINES_DTO.size()));
    }

    @Test
    void shouldAddLine() throws Exception {
        when(lineService.create(any(), any())).thenReturn(LINE_DTO);
        mockMvc.perform(post(ApiPaths.LINE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(LINE_DTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(LINE_DTO.getName()));
    }

    @Test
    void shouldUpdateLine() throws Exception {
        when(lineService.update(anyLong(), any())).thenReturn(LINE_DTO);
        mockMvc.perform(put(ApiPaths.LINE + "/" + LINE_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(LINE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(LINE_DTO.getName()));
    }

    @Test
    void shouldDeleteLine() throws Exception {
        doNothing().when(lineService).deleteById(anyLong());
        mockMvc.perform(delete(ApiPaths.LINE + "/" + LINE_DTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundOnDelete() throws Exception {
        doThrow(ApiException.notFound()).when(lineService).deleteById(anyLong());
        mockMvc.perform(delete(ApiPaths.LINE + "/" + LINE_DTO.getId()))
                .andExpect(status().isNotFound());
    }
}
