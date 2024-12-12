package com.example.server.repository;

import com.example.server.models.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {
    Optional<SystemConfiguration> findTopByOrderByIdAsc();
}
