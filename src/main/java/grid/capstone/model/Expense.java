package grid.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonIgnore
    private Patient patient;


    public void updateObject(Expense expense) {
        updateHelper(expense.getName(), this::setName);
        updateHelper(expense.getCategory(), this::setCategory);
        updateHelper(expense.getDescription(), this::setDescription);
        updateHelper(expense.getAmount(), this::setAmount);
        updateHelper(expense.getPaid(), this::setPaid);
    }

    private <T> void updateHelper(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
