package com.example.server.controllers;

import com.example.server.models.Event;
import com.example.server.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/purchased-tickets/{customerId}")
    public List<Event> getPurchasedTicketsByCustomer(@PathVariable int customerId) {
        return customerService.getPurchasedTicketsByCustomer(customerId);
    }

    @PostMapping("/purchase/{customerId}/{eventId}/{tickets}")
    public String purchaseTicket(@PathVariable int customerId, @PathVariable Long eventId, @PathVariable int tickets) {
        System.out.println("Customer " + customerId + " purchasing " + tickets + " tickets for event " + eventId);
        return customerService.purchaseTicket(customerId, eventId, tickets);
    }
}
