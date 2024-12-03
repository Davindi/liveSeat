package com.example.server.controllers;


import com.example.server.models.SystemConfiguration;
import com.example.server.service.SystemConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/configuration")
@Tag(name = "SystemConfiguration", description = "Endpoints for System configuration")
public class SystemConfigurationController {

    private final SystemConfigurationService service;

    public SystemConfigurationController(SystemConfigurationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<SystemConfiguration> getConfiguration() {
        SystemConfiguration config = service.getSystemConfiguration();
        if (config == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(config);
    }

    @PostMapping
    public ResponseEntity<SystemConfiguration> createOrUpdateConfiguration(@RequestBody SystemConfiguration newConfig) {
        SystemConfiguration updatedConfig = service.createOrUpdateConfiguration(newConfig);
        return ResponseEntity.ok(updatedConfig);
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSystem() {
        if (service.canStartSystem()) {
            service.startSystem();
            return ResponseEntity.ok("System started successfully.");
        }
        return ResponseEntity.badRequest().body("Cannot start system without a configuration.");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        service.stopSystem();
        return ResponseEntity.ok("System stopped successfully.");
    }
}