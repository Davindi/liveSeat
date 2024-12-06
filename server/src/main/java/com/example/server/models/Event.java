package com.example.server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name", nullable = true, length = 255)
    private String eventName;

    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Column(name = "ticket_price", nullable = false)
    private double ticketPrice;

    @Column(name = "tickets_sold", nullable = false)
    private int ticketsSold;

    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;
    public Event() {}
    public Event(Long id, String eventName, String status, double ticketPrice, int ticketsSold, int totalTickets) {
        this.id = id;
        this.eventName = eventName;
        this.status = status;
        this.ticketPrice = ticketPrice;
        this.ticketsSold = ticketsSold;
        this.totalTickets = totalTickets;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }
}
