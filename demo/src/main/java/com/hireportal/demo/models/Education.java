package com.hireportal.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long educationId;

    @Column(name = "degree", nullable = false)
    private String degree;

    @Column(name = "institution", nullable = false)
    private String institution;

    @Column(name = "start_year", nullable = false)
    private int startYear;

    @Column(name = "end_year", nullable = false)
    private int endYear;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}