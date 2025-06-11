package hr.tvz.napredna.java.dijezetserver.mapper;

import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.model.Station;

public final class StationMapper {
    public StationMapper() {
    }

    public static StationDto toDto(Station station) {
        return new StationDto(
                station.getId(),
                station.getName()
        );
    }
}
