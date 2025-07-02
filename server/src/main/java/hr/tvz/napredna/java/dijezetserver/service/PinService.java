package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.PinDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.request.PinRequest;

import java.util.List;

public interface PinService extends EntityService<PinDto, PinRequest> {
    @Override
    List<PinDto> findAll();

    @Override
    PinDto create(PinRequest pinRequest, User user);

    @Override
    PinDto update(Long id, PinRequest lineDto);

    @Override
    void deleteById(Long id);

    void deleteByIds(List<Long> ids);
}
