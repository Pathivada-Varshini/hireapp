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
@Table(name = "skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @Column(name = "skill_description", nullable = true)
    private String skillDescription;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}