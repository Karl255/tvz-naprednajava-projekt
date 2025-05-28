package hr.tvz.napredna.java.dijezetserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LineDto {
    private BigInteger id;
    private String name;
}
