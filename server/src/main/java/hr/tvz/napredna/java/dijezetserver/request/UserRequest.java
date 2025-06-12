package hr.tvz.napredna.java.dijezetserver.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    @NotBlank
    private String username;
    @Length(min = 8)
    private String password;
}
