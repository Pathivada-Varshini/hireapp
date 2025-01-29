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
@Table(name = "summary")
public class Summary {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "summary_id")
        private Long summaryId;

        @Column(name = "summary_text", nullable = false)
        private String summaryText;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;
}