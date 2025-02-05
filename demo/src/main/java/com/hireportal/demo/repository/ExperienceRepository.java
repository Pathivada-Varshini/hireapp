package com.hireportal.demo.repository;

import com.hireportal.demo.models.Experience;
import com.hireportal.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByUser(User user);
    void deleteByUser(User user);
}