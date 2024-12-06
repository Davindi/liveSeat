package com.example.server.controllers;


import com.example.server.models.SystemConfiguration;
import com.example.server.service.SystemConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Map<String, String>> startSystem() {
        Map<String, String> response = new HashMap<>();
        if (service.canStartSystem()) {
            service.startSystem();
            response.put("message", "System started successfully.");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Cannot start system without a configuration.");
        return ResponseEntity.badRequest().body(response);
    }


    @PostMapping("/stop")
    public ResponseEntity<Map<String, String>> stopSystem() {
        service.stopSystem();
        Map<String, String> response = new HashMap<>();
        response.put("message", "System stopped successfully.");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get system status", description = "Returns whether the system is started or not.")
    @GetMapping("/status")
    public ResponseEntity<Boolean> getSystemStatus() {
        SystemConfiguration config = service.getSystemConfiguration();
        if (config == null) {
            return ResponseEntity.badRequest().body(false); // Return false if no configuration exists
        }
        return ResponseEntity.ok(config.getSystemStarted());
    }

}