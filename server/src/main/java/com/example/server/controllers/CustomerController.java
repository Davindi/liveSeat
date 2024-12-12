package com.example.server.controllers;

import com.example.server.dto.EventTicket;
import com.example.server.models.Event;
import com.example.server.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/events")
    public List<Event> getAvailableEvents() {
        return customerService.getAllEvents();
    }
//    @GetMapping("/purchased-tickets/{customerId}")
//    public List<Event> getPurchasedTicketsByCustomer(@PathVariable int customerId) {
//        return customerService.getPurchasedTicketsByCustomer(customerId);
//    }
    @GetMapping("/purchased-tickets/{customerId}")
    public ResponseEntity<List<EventTicket>> getPurchasedTicketsByCustomer(@PathVariable int customerId) {
        try {
            List<EventTicket> tickets = customerService.getPurchasedTicketsByCustomer(customerId);
            return ResponseEntity.ok(tickets); // Ensure this returns a JSON response
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/purchase/{customerId}/{eventId}/{tickets}")
    public ResponseEntity<?>  purchaseTicket(@PathVariable int customerId, @PathVariable Long eventId, @PathVariable int tickets) {
        System.out.println("Customer " + customerId + " purchasing " + tickets + " tickets for event " + eventId);
        String message = customerService.purchaseTicket(customerId, eventId, tickets);

        return ResponseEntity.ok().body(Map.of("message", message));
    }
}
