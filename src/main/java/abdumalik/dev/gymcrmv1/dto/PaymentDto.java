package abdumalik.dev.gymcrmv1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private String name;
    private int monthsPaid;
    private Double pricePerMonth = 0.0;

}