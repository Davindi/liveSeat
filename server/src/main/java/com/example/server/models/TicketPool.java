package com.example.server.models;

import com.example.server.repository.EventRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketPool {
    private final Map<String, Event> eventMap = new HashMap<>();
    private final SystemConfiguration config;
    private final EventRepository eventRepository;
    private  List<Event> events = new ArrayList<>();
    public TicketPool(SystemConfiguration config, EventRepository eventRepository) {
        this.config = config;
        this.eventRepository = eventRepository;
        this.events = eventRepository.findAll();
    }

    public synchronized void addEvent(Event event) {
        // Set initial status as "Active" when the event is created
        event.setStatus("Active");
        eventMap.put(event.getEventName(), event);
        System.out.println("Event added: " + event.getEventName() + " with status: " + event.getStatus());
    }
    public synchronized List<Event> getAllEvents() {
        //return new ArrayList<>(eventMap.values());
        return eventRepository.findAllActiveEvents();
    }
    public synchronized void changeEventStatus(String eventName, String status) {
        Event event = eventMap.get(eventName);
        if (event != null) {
            event.setStatus(status);
            System.out.println("Status of " + eventName + " changed to: " + status);
        } else {
            System.out.println("Event not found.");
        }
    }

    public synchronized void addTickets(Long id, int additionalTickets) {
        Event event = eventMap.get(id);
        if (event != null) {
            int newTicketCount = event.getTotalTickets() + additionalTickets;
            event.setTotalTickets(newTicketCount);
            System.out.println("Added " + additionalTickets + " tickets to " + id + ". Total tickets: " + newTicketCount);
        } else {
            System.out.println("Event not found.");
        }
    }
    public synchronized void releaseTickets(Long eventId, int additionalTickets) {
        Event event = eventMap.values().stream()
                .filter(e -> e.getId().equals(eventId))
                .findFirst()
                .orElse(null);

        if (event == null) {
            System.out.println("Error: Event not found with ID: " + eventId);
            return;
        }

        if (!event.getStatus().equals("Active")) {
            System.out.println("Error: Event with ID " + eventId + " is not active.");
            return;
        }

        // Add tickets
        int newTicketCount = event.getTotalTickets() + additionalTickets;
        event.setTotalTickets(newTicketCount);

        System.out.println("Added " + additionalTickets + " tickets to Event ID: " + eventId + ". Total tickets: " + newTicketCount);
    }


    public synchronized void displayEventStatus() {
        for (Event event : eventMap.values()) {
            System.out.println(event);
        }
    }
    public synchronized List<Event> getEventsByVendor(int vendorId) {
        System.out.println("Vendor ID: " + vendorId);
        System.out.println("Current Events in Map: " + eventMap.values());
        return eventMap.values().stream()
                .filter(event -> event.getUser() != null && event.getUser().getId() == vendorId)
                .collect(Collectors.toList());
    }
    public synchronized List<Event> getEventsByVendorId(Long vendorId) {
        return eventMap.values().stream()
                .filter(event -> event.getUser().getId() == vendorId)  // Filter by vendor ID
                .collect(Collectors.toList());
    }

    public boolean removeTickets(Long eventId, int tickets) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null && event.getRemainingTickets() >= tickets) {
            event.setTicketsSold(event.getTicketsSold() + tickets);
            eventRepository.save(event);
            return true;
        }
        return false;
    }


}
