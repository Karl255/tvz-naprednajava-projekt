package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.PinDto;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.request.PinRequest;

import java.util.List;

public interface PinService {
    List<PinDto> findAll();

    PinDto save(PinRequest pinRequest, User user);

    PinDto update(Long id, PinRequest lineDto);

    void deleteById(Long id);
}
