package com.example.server.controllers;

import com.example.server.dto.EventRequest;
import com.example.server.models.Event;
import com.example.server.service.VendorService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {


    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    @GetMapping("/events")
    public List<Event> getVendorEvents() {
        return vendorService.getVendorEvents();
    }

    @PostMapping("/add-event")
    public ResponseEntity<?> addEvent(@RequestBody EventRequest eventRequest) {
        System.out.println("Raw Payload: " + eventRequest.getEventName());

//        if (eventRequest == null) {
//            System.out.println("EventRequest object is null!");
//        } else {
//            System.out.println("EventName: " + eventRequest.getEventName());
//            System.out.println("TotalTickets: " + eventRequest.getTotalTickets());
//            System.out.println("TicketPrice: " + eventRequest.getTicketPrice());
//        }

        try {
            Event event = vendorService.addEvent(eventRequest);
            System.out.println("Event added: " + event.getEventName());
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}

