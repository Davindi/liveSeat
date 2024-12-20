package com.example.server.service;

import com.example.server.controllers.ActivityController;
import com.example.server.dto.EventTicket;
import com.example.server.enums.UserRole;
import com.example.server.models.*;
import com.example.server.repository.EventRepository;
import com.example.server.repository.TicketRepository;
import com.example.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CustomerService {
    private final TicketPool ticketPool;
    private final ExecutorService executorService;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final ActivityController activityController;
//    public CustomerService(TicketPool ticketPool) {
//        this.ticketPool = ticketPool;
//        this.executorService = Executors.newFixedThreadPool(10); // 10 customers
//    }
    @Autowired
    private UserRepository userRepository;

    public CustomerService(SystemConfigurationService configService, EventRepository eventRepository,TicketRepository ticketRepository , ActivityController activityController ) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.activityController = activityController;
        SystemConfiguration config = configService.getSystemConfiguration();
        if (config == null) {
            throw new IllegalStateException("System configuration not found. Please set up the configuration before starting vendors.");
        }
        this.ticketPool = new TicketPool(config , eventRepository,activityController);
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void startCustomers(Long eventId) {
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(i, "Customer " + i, ticketPool, eventId);
            executorService.execute(customer);
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
    public List<Event> getAllEvents() {
        return ticketPool.getAllEvents();
    }
    public List<EventTicket> getPurchasedTicketsByCustomer(int customerId) {
        return eventRepository.findPurchasedTicketsWithQuantityByCustomerId(customerId);
    }

    public String purchaseTicket(int customerId,Long eventId, int tickets ) {
        if (!ticketPool.canCustomerPurchase((long) customerId, tickets)) {
            activityController.broadcastActivity("Ticket purchase limit exceeded. Please wait for the next available purchase window.");
            return "Ticket purchase limit exceeded. Please wait for the next available purchase window.";
        }
        boolean success = ticketPool.removeTickets(eventId, tickets);
        if (success) {

            User customer = userRepository.findById(customerId)
                    .filter(user -> user instanceof User && UserRole.customer.equals(((User) user).getRole())) // Cast to User
                    .map(user -> (User) user)
                    .orElseThrow(() -> new RuntimeException("No customer found with the provided userId"));
            // Retrieve event as an Event object
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            // Create and save the ticket
            Ticket ticket = new Ticket();
            ticket.setCustomer(customer); // Set the customer
            ticket.setEvent(event);       // Set the event
            ticket.setQuantity(tickets);  // Set the ticket quantity
            ticketRepository.save(ticket);
            // Broadcast activity
            activityController.broadcastActivity("Successfully purchased " + tickets + " ticket(s) for Event ID: " + eventId);
            return "Successfully purchased " + tickets + " ticket(s) for Event ID: " + eventId;
        } else {
            return "Failed to purchase ticket(s). Please check availability.";
        }
    }

}
