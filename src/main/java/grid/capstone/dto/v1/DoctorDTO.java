package grid.capstone.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Javaughn Stephenson
 * @since 19/06/2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO implements Serializable {
    private Long id;
    private String username;
    private String department;
    private String biography;
}
