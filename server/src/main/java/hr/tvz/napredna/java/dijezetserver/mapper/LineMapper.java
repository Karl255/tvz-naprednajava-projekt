package hr.tvz.napredna.java.dijezetserver.mapper;

import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.model.Line;

public final class LineMapper {

    public LineMapper() {
    }

    public static LineDto toDto(Line line) {
        return new LineDto(line.getId(), line.getName());
    }

    public static Line toEntity(LineDto lineDto) {
        return new Line(lineDto.getName());
    }
}
