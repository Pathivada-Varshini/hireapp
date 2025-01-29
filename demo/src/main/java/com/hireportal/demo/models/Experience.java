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
@Table(name = "experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    private Long experienceId;

    @Column(name = "job_position", nullable = false)
    private String jobPosition;

    @Column(name = "office_name", nullable = false)
    private String officeName;

    @Column(name = "start_date", nullable = false)
    private int startDate;

    @Column(name = "end_date", nullable = false)
    private int endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}