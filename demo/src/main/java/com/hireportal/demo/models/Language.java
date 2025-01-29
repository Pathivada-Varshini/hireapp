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
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Long languageId;

    @Column(name = "language_name", nullable = false)
    private String languageName;

    @Column(name = "proficiency", nullable = false)
    private String proficiency;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}