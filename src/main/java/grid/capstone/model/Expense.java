package grid.capstone.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String description;
    private Double amount;
    private LocalDate dateOfExpense;
    private Boolean paid;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
