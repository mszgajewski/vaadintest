package com.example.application.data.repository;

import com.example.application.data.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
}
