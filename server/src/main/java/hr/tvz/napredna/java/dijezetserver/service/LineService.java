package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.model.User;

import java.util.List;

public interface LineService extends EntityService<LineDto, LineDto> {
    @Override
    List<LineDto> findAll();

    @Override
    LineDto create(LineDto lineDto, User user);

    @Override
    LineDto update(Long id, LineDto lineDto);

    @Override
    void deleteById(Long id);
}
