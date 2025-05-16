package abdumalik.dev.gymcrmv1.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Email
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

}