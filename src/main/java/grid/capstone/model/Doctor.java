package grid.capstone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
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
