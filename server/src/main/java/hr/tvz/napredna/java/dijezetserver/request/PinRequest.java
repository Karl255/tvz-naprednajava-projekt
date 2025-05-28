package hr.tvz.napredna.java.dijezetserver.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PinRequest {
    private Long stationId;
    private Long lineId;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
