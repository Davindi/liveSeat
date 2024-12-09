package com.example.server.models;

import java.util.List;

public class Vendor implements Runnable {
    private Long id;
    private final String vendorName;
    private final TicketPool ticketPool;

    public Vendor(String vendorName, TicketPool ticketPool, Long id) {
        this.vendorName = vendorName;
        this.ticketPool = ticketPool;
        this.id = id;
    }

    @Override
    public void run() {
        // Get events related to this vendor's id
        List<Event> vendorEvents = ticketPool.getEventsByVendorId(this.id);

        // Simulate releasing tickets for each event
        for (Event event : vendorEvents) {
            ticketPool.releaseTickets(event.getId(), 50); // Release 50 tickets for each event
        }
    }


}
