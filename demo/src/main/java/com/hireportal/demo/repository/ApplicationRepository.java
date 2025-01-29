package com.hireportal.demo.repository;

import com.hireportal.demo.models.Application;
import com.hireportal.demo.models.Job;
import com.hireportal.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
    List<Application> findByJob(Job job);
}