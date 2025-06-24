package hr.tvz.napredna.java.dijezetserver.controller;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.config.ApiPaths;
import hr.tvz.napredna.java.dijezetserver.dto.CommentDto;
import hr.tvz.napredna.java.dijezetserver.service.CommentService;
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

public class CommentControllerTest extends BaseTest {
    static final List<CommentDto> COMMENTS_DTO = List.of(new CommentDto(COMMENT.getId(), COMMENT.getContent(), COMMENT.getUser().getUsername(), null, COMMENT.getIssueType(), null, List.of()));
    static final CommentDto COMMENT_DTO = COMMENTS_DTO.getFirst();
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Test
    void shouldGetComments() throws Exception {
        when(commentService.findAll()).thenReturn(COMMENTS_DTO);
        mockMvc.perform(get(ApiPaths.COMMENT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(COMMENTS_DTO.size()));
    }

    @Test
    void shouldCreateComment() throws Exception {
        when(commentService.create(any())).thenReturn(COMMENT_DTO);
        mockMvc.perform(post(ApiPaths.COMMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(COMMENT_DTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value(COMMENT_DTO.getContent()));
    }

    @Test
    void shouldReturnBadRequestOnCreate() throws Exception {
        doThrow(new EntityNotFoundException()).when(commentService).create(any());
        mockMvc.perform(post(ApiPaths.COMMENT))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateComment() throws Exception {
        when(commentService.update(anyLong(), any())).thenReturn(COMMENT_DTO);
        mockMvc.perform(put(ApiPaths.COMMENT + "/" + COMMENT_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(COMMENT_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(COMMENT_DTO.getId()))
                .andExpect(jsonPath("$.content").value(COMMENT_DTO.getContent()));
    }
    @Test
    void shouldReturnBadRequestOnUpdate() throws Exception {
        doThrow(new EntityNotFoundException()).when(commentService).update(anyLong(), any());
        mockMvc.perform(put(ApiPaths.COMMENT + "/" + COMMENT_DTO.getId()))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void shouldDeleteComment() throws Exception {
        doNothing().when(commentService).deleteById(anyLong());
        mockMvc.perform(delete(ApiPaths.COMMENT + "/" + COMMENT_DTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundOnDelete() throws Exception {
        doThrow(new EntityNotFoundException()).when(commentService).deleteById(anyLong());
        mockMvc.perform(delete(ApiPaths.COMMENT + "/" + COMMENT_DTO.getId()))
                .andExpect(status().isNotFound());
    }
}
