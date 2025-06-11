package hr.tvz.napredna.java.dijezetserver.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PinRequest {
    @Nullable
    private Long stationId;
    @Nullable
    private Long lineId;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
