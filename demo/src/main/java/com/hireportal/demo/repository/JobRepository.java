package com.hireportal.demo.repository;

import com.hireportal.demo.enums.ExperienceRequired;
import com.hireportal.demo.enums.JobType;
import com.hireportal.demo.enums.SalaryRange;
import com.hireportal.demo.models.Job;
import com.hireportal.demo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCategory(Category category);
    List<Job> findByLocation(String location);
    List<Job> findByCompanyName(String companyName);
    @Query("SELECT j FROM Job j WHERE " +
            "(?1 IS NULL OR j.experienceRequired = ?1) AND " +
            "(?2 IS NULL OR j.salaryRange = ?2) AND " +
            "(?3 IS NULL OR j.jobType = ?3)")
    List<Job> findFilteredJobs(ExperienceRequired experienceRequired, SalaryRange salaryRange, JobType jobType);
}