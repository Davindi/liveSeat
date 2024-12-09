package com.example.server.controllers;

import com.example.server.dto.EventRequest;
import com.example.server.models.Event;
import com.example.server.service.VendorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {


    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    // Get events by vendor ID
    @GetMapping("/{vendorId}/events")
    public ResponseEntity<List<Event>> getVendorEvents(@PathVariable int vendorId) {
        System.out.println(vendorId);
        List<Event> events = vendorService.getVendorEvents(vendorId);
        if (events.isEmpty()) {
            System.out.println("no events");
            return ResponseEntity.noContent().build();  // If no events found
        }
        return ResponseEntity.ok(events);  // Return the events
    }

    @PostMapping("/{vendorId}/add-event")
    public ResponseEntity<?> addEvent(@PathVariable int vendorId,@RequestBody EventRequest eventRequest) {
        System.out.println("Raw Payload: " + eventRequest);

//        if (eventRequest == null) {
//            System.out.println("EventRequest object is null!");
//        } else {
//            System.out.println("EventName: " + eventRequest.getEventName());
//            System.out.println("TotalTickets: " + eventRequest.getTotalTickets());
//            System.out.println("TicketPrice: " + eventRequest.getTicketPrice());
//        }

        try {
            vendorService.addEvent(vendorId,eventRequest);
//            System.out.println("Event added: " + event.getEventName());
            return  ResponseEntity.ok(eventRequest);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Add more tickets to an event
    @PutMapping("/events/{id}/addTickets")
    public ResponseEntity<String> addMoreTickets(@PathVariable Long id, @RequestParam int additionalTickets) {
        try {
            vendorService.releaseMoreTickets(id, additionalTickets);
            return ResponseEntity.ok("{\"message\": \"Tickets added successfully\"}");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add tickets: " + e.getMessage());
        }
    }

    @PutMapping("/events/{eventId}/status")
    public ResponseEntity<String> updateEventStatus(@PathVariable Long eventId, @RequestParam String status) {
        try {
            vendorService.updateEventStatus(eventId, status);

            return ResponseEntity.ok("{\"message\": \"Event status updated successfully.\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}

