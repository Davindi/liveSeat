package com.example.server.service;


import com.example.server.models.SystemConfiguration;
import com.example.server.repository.SystemConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemConfigurationService {

    private final SystemConfigurationRepository repository;

    public SystemConfigurationService(SystemConfigurationRepository repository) {
        this.repository = repository;
    }

    public SystemConfiguration getSystemConfiguration() {
        return repository.findTopByOrderByIdAsc().orElse(null);
    }

    public SystemConfiguration createOrUpdateConfiguration(SystemConfiguration newConfig) {
        Optional<SystemConfiguration> existingConfig = repository.findTopByOrderByIdAsc();

        if (existingConfig.isPresent()) {
            // Update the existing configuration
            SystemConfiguration config = existingConfig.get();
            config.setTotalTickets(newConfig.getTotalTickets());
            config.setTicketReleaseRate(newConfig.getTicketReleaseRate());
            config.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());
            config.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());
            return repository.save(config);
        } else {
            // Create new configuration
            return repository.save(newConfig);
        }
    }

    public boolean canStartSystem() {
        return repository.findTopByOrderByIdAsc().isPresent();
    }

    public void startSystem() {
        SystemConfiguration config = repository.findTopByOrderByIdAsc()
                .orElseThrow(() -> new IllegalStateException("No configuration found. Cannot start system."));
        config.setSystemStarted(true);
        repository.save(config);
        System.out.println("System started: " + config.getSystemStarted()); // Log for debugging
    }


    public void stopSystem() {
        SystemConfiguration config = repository.findTopByOrderByIdAsc()
                .orElseThrow(() -> new IllegalStateException("No configuration found."));
        config.setSystemStarted(false);
        repository.save(config);
    }


}