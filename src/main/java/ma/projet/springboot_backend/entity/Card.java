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

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "school_year")
    private String schoolYear;

    @Column(name = "student_level")
    private String studentLevel;

    @Column(name = "school_address")
    private String schoolAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
