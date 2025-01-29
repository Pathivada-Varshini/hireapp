package com.hireportal.demo.repository;

import com.hireportal.demo.models.Job;
import com.hireportal.demo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCategory(Category category);
    List<Job> findByLocation(String location);
    List<Job> findByCompanyName(String companyName);
}