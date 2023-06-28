package grid.capstone.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Javaughn Stephenson
 * @since 22/06/2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDTO {

    private String name;
    private String category;
    private String description;
    private BigDecimal amount;

}
