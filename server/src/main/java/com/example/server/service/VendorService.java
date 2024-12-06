package com.example.server.service;

import com.example.server.dto.EventRequest;
import com.example.server.models.Event;
import com.example.server.models.SystemConfiguration;
import com.example.server.repository.EventRepository;
import com.example.server.repository.SystemConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SystemConfigurationRepository systemConfigurationRepository;


    public List<Event> getVendorEvents() {
        // Fetch only events linked to active system configuration
        return eventRepository.findAll();
    }
    public VendorService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event addEvent(EventRequest eventRequest) {
        // Check if the system is started
        Optional<SystemConfiguration> config = systemConfigurationRepository.findTopByOrderByIdAsc();
        System.out.println("System started: " + config.isPresent());
        if (config.isEmpty() || !config.get().getSystemStarted()) {
            throw new IllegalStateException("System is not started by admin.");
        }
        System.out.println( "ticket price"+ eventRequest);
        // Validate ticket price
        if (eventRequest.getTicketPrice() <= 0) {
            throw new IllegalArgumentException("Ticket price must be greater than 0.");
        }

        Event event = new Event();
        event.setEventName(eventRequest.getEventName());
        event.setTotalTickets(eventRequest.getTotalTickets());
        event.setTicketsSold(0);
        event.setTicketPrice(eventRequest.getTicketPrice());
        event.setStatus("Not Started");

        return eventRepository.save(event);
    }
}

