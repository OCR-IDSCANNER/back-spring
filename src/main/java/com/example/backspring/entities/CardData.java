package com.example.backspring.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "card_data")
public class CardData {

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

    @Column(name = "user_id")
    private Long userId;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
