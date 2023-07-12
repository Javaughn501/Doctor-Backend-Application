package grid.capstone.dto.v1;

import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Address is required")
    private String address;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Age is required")
    private Integer age;

    private String bloodGroup;

    private String religion;

    private String occupation;

    @NotNull(message = "Gender is required")
    @Pattern(regexp = "[MF]", message = "Gender should be 'M' or 'F'")
    private String gender;

    private String maritalStatus;

    @Size(max = 100, message = "Description should be at most 100 characters")
    private String description;
}
