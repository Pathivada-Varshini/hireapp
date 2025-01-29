package com.hireportal.demo.models;

import java.util.Date;

import com.hireportal.demo.enums.ExperienceRequired;
import com.hireportal.demo.enums.JobType;
import com.hireportal.demo.enums.SalaryRange;
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
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "job_description", nullable = false)
    private String jobDescription;

    @Column(name = "skills_required", nullable = false)
    private String skillsRequired;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_range", nullable = false)
    private SalaryRange salaryRange;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_required", nullable = false)
    private ExperienceRequired experienceRequired;

    @Column(name = "location", nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(name = "post_date", nullable = false)
    private Date postDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;
}