package abdumalik.dev.gymcrmv1.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDto {

    private String firstName;
    private String lastName;
    private String username;
    @Email
    private String email;
    private String password;
    private Double price;
    private UUID userId;

}