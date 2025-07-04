package hr.tvz.napredna.java.dijezetserver.dto;

import hr.tvz.napredna.java.dijezetserver.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private UserRole role;
}
