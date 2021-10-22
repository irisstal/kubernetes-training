package com.example.servicesets.repository;

import com.example.servicesets.entity.Sets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetsRepository extends JpaRepository<Sets, Long> {
}
