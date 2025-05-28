package hr.tvz.napredna.java.dijezetserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StationDto {
    private BigInteger id;
    private String name;
}
