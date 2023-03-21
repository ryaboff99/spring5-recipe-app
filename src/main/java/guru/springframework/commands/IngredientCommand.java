package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;  // tels to what Recipe it belongs to

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @DecimalMin("0.1")
    @DecimalMax("100")
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
}
