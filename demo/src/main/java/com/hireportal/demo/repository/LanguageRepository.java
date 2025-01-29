package com.hireportal.demo.repository;

import com.hireportal.demo.models.Language;
import com.hireportal.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findByUser(User user);
}