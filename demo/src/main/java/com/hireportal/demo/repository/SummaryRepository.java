package com.hireportal.demo.repository;

import com.hireportal.demo.models.Summary;
import com.hireportal.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SummaryRepository extends JpaRepository<Summary, Long> {
    Optional<Summary> findByUser(User user);
    void deleteByUser(User user);
}