package grid.capstone.model;

import jakarta.persistence.*;
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
    private String username;
    private String email;
    private String phoneNumber;
    private String specialization;
    private List<String> skills;
    private String biography;
    private String department;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Availability> availabilities;
}
