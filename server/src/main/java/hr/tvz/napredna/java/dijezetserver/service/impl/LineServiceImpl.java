package hr.tvz.napredna.java.dijezetserver.service.impl;

import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.mapper.LineMapper;
import hr.tvz.napredna.java.dijezetserver.model.Line;
import hr.tvz.napredna.java.dijezetserver.repository.LineRepository;
import hr.tvz.napredna.java.dijezetserver.service.LineService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;

    @Override
    public List<LineDto> findAll() {
        return lineRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public LineDto save(LineDto lineDto) {
        return toDto(lineRepository.save(toEntity(lineDto)));
    }

    @Override
    public LineDto update(Long id, LineDto lineDto) {
        Optional<Line> lineOptional = lineRepository.findById(id);
        if (lineOptional.isPresent()) {
            Line line = lineOptional.get();
            line.setName(lineDto.getName());
            lineRepository.save(line);
            return toDto(line);
        } else {
            throw new EntityNotFoundException("Line with id " + id + " not found");
        }
    }

    @Override
    public void deleteById(Long id) {
        lineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Line with id " + id + " not found."));
        lineRepository.deleteById(id);
    }

    private LineDto toDto(Line line) {
        return LineMapper.toDto(line);
    }

    private Line toEntity(LineDto lineDto) {
        return LineMapper.toEntity(lineDto);
    }
}
