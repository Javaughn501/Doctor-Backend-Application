package grid.capstone.dto.v1;

import jakarta.annotation.sql.DataSourceDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Javaughn Stephenson
 * @since 20/06/2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {
    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private Integer age;
    private String bloodGroup;
    private String religion;
    private String occupation;
    private Character gender;
    private String maritalStatus;
    private String description;
}
