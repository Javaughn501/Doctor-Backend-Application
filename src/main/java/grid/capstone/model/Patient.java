package grid.capstone.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private Integer age;
    private String bloodGroup;
    private String religion;
    private String occupation;
    private Character gender;
    private String maritialStatus;
    private String description;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
}
