package com.example.server.service;

import com.example.server.controllers.ActivityController;
import com.example.server.dto.EventRequest;
import com.example.server.enums.UserRole;
import com.example.server.models.*;
import com.example.server.repository.EventRepository;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class VendorService {
    private final TicketPool ticketPool;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // 5 vendors
    private final EventRepository eventRepository;
    private final ActivityController activityController;

    @Autowired
    private UserRepository userRepository;

    public VendorService(SystemConfigurationService configService, EventRepository eventRepository, ActivityController activityController) {
        this.eventRepository = eventRepository;
        this.activityController = activityController;
        SystemConfiguration config = configService.getSystemConfiguration();
        if (config == null) {
            throw new IllegalStateException("System configuration not found. Please set up the configuration before starting vendors.");
        }
        this.ticketPool = new TicketPool(config, eventRepository,activityController);
    }

//    public void startVendors() {
//        for (int i = 1; i <= 5; i++) { // 5 vendors
//            Vendor vendor = new Vendor("Vendor " + i, ticketPool);
//            executorService.execute(vendor);
//        }
//    }

    public void displayEventStatuses() {
        ticketPool.displayEventStatus();
    }

    public void shutdown() {
        executorService.shutdown();
    }
    public List<Event> getVendorEvents(int vendorId) {
        //return ticketPool.getEventsByVendor(vendorId);
        return eventRepository.findByUser_Id(vendorId);
    }

    public void addEvent(int vendorId,EventRequest eventRequest) {
//        String vendorName = eventRequest.getVendorName();
//        String eventName = eventRequest.getEventName();
//        int ticketCount = eventRequest.getTotalTickets();
        User vendor = userRepository.findById(vendorId)
                .filter(user -> user instanceof User && UserRole.vendor.equals(((User) user).getRole())) // Cast to User
                .map(user -> (User) user)
                .orElseThrow(() -> new RuntimeException("No vendor found with the provided userId"));

        Event event = new Event();
        event.setUser(vendor);
        //event.setVendorName(eventRequest.getVendorName());
        event.setEventName(eventRequest.getEventName());
        event.setStatus("Active");
        event.setTicketPrice(eventRequest.getTicketPrice());
        event.setTotalTickets(eventRequest.getTotalTickets());
        event.setTicketsSold(eventRequest.getTicketsSold());
        event.setImageUrl(eventRequest.getImageUrl());

        // Save to DB
        eventRepository.save(event);
        System.out.println("Event added: " + event.getEventName());
        // Broadcast activity
        activityController.broadcastActivity("sucessfully added event" + event.getEventName());
        // Call the addEvent method in TicketPool
        ticketPool.addEvent(event);
    }

    public void updateEventStatus(Long eventId, String status) {
        // Fetch the event by ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));

        // Update the status
        event.setStatus(status);

        // Save the updated event back to the database
        eventRepository.save(event);
        // Broadcast activity
        activityController.broadcastActivity("sucessfully updated event" + event.getEventName());
        System.out.println("Event status updated: ID=" + eventId + ", New Status=" + status);
    }

    public void releaseMoreTickets(Long eventId, int additionalTickets) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));

        // Synchronize with the TicketPool
        ticketPool.releaseTickets(eventId, additionalTickets);

        // Update the database
        event.setTotalTickets(event.getTotalTickets() + additionalTickets);
        eventRepository.save(event);
        // Broadcast activity
        activityController.broadcastActivity("add"+ additionalTickets + "to "+event.getEventName());
        System.out.println("Updated tickets for Event ID: " + eventId + " in the database.");
    }



}
