package grid.capstone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@ToString(exclude = {"appointments"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Age is required")
    private Integer age;

    private String bloodGroup;

    private String religion;

    private String occupation;

    @NotNull(message = "Gender is required")
    @Pattern(regexp = "[MF]", message = "Gender should be 'M' or 'F'")
    private Character gender;

    private String maritalStatus;

    @Size(max = 100, message = "Description should be at most 100 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords;
}
