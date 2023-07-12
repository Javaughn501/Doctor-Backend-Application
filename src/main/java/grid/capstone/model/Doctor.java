package grid.capstone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@ToString(exclude = {"availabilities"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String specialization;

    @NotNull(message = "Skills list is required")
    @Size(min = 1, message = "Doctor's skills list must have at least one skill")
    private List<String> skills;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Availability> availabilities;
}
