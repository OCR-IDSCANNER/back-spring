package ma.projet.springboot_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String student_id;
    private String name;
    private int school_year;
    private String school_adress;
    private String student_level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
