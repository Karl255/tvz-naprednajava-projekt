package hr.tvz.napredna.java.dijezetserver.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PinDto {
    private Long id;
    @Nullable private StationDto station;
    @Nullable private LineDto line;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String user;
}
