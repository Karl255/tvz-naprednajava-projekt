package hr.tvz.napredna.java.dijezetserver.mapper;

import hr.tvz.napredna.java.dijezetserver.dto.LineDto;
import hr.tvz.napredna.java.dijezetserver.dto.PinDto;
import hr.tvz.napredna.java.dijezetserver.dto.StationDto;
import hr.tvz.napredna.java.dijezetserver.model.Line;
import hr.tvz.napredna.java.dijezetserver.model.Pin;
import hr.tvz.napredna.java.dijezetserver.model.Station;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.request.PinRequest;

public final class PinMapper {

    public PinMapper() {
    }

    public static PinDto toDto(Pin pin, StationDto stationDto, LineDto lineDto) {
        return new PinDto(
                pin.getId(),
                stationDto,
                lineDto,
                pin.getLatitude(),
                pin.getLongitude(),
                pin.getUser() != null ? pin.getUser().getUsername() : null
        );
    }

    public static Pin toEntity(PinRequest pinRequest, Station station, Line line, User user) {
        return new Pin(
                null,
                station,
                line,
                pinRequest.getLatitude(),
                pinRequest.getLongitude(),
                user
        );
    }
}
