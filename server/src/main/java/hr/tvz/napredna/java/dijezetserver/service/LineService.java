package hr.tvz.napredna.java.dijezetserver.service;

import hr.tvz.napredna.java.dijezetserver.dto.LineDto;

import java.math.BigInteger;
import java.util.List;

public interface LineService {
    List<LineDto> findAll();
    LineDto save(LineDto lineDto);
    LineDto update(BigInteger id, LineDto lineDto);
    void deleteById(BigInteger id);
}
