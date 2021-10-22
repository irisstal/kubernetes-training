package com.example.servicedj.repository;

import com.example.servicedj.entity.DJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DJRepository extends JpaRepository<DJ, Long> {}

