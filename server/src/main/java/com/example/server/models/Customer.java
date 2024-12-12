package com.example.server.models;
import java.util.Random;

public class Customer implements Runnable {
    private final int id;  // Customer ID
    private final String name;  // Customer Name
    private final TicketPool ticketPool;  // Shared Ticket Pool
    private final Long eventId;  // Event for ticket purchase

    public Customer(int id, String name, TicketPool ticketPool, Long eventId) {
        this.id = id;
        this.name = name;
        this.ticketPool = ticketPool;
        this.eventId = eventId;
    }

    @Override
    public void run() {
        Random random = new Random();
        int ticketsToBuy = random.nextInt(5) + 1;  // Customers try to buy 1-5 tickets

        boolean success = ticketPool.removeTickets(eventId, ticketsToBuy);
        if (success) {
            System.out.println(name + " successfully purchased " + ticketsToBuy + " tickets for Event ID: " + eventId);
        } else {
            System.out.println(name + " failed to purchase tickets for Event ID: " + eventId + " (Insufficient tickets).");
        }
    }
}
