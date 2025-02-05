package com.hireportal.demo.repository;

import com.hireportal.demo.models.Skills;
import com.hireportal.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {
    List<Skills> findByUser(User user);
    void deleteByUser(User user);
}