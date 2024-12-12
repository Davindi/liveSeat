package com.example.server.models;

import jakarta.persistence.*;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(name = "vender_name", nullable = true, length = 255)
//    private String vendorName;
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false) // Assuming `User` represents a vendor
    private User user;

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

    @Column(name = "event_image", nullable = true)
    private String imageUrl;
    @Transient
    public int getVendorId() {
        return user != null ? user.getId() : null;
    }
    public Event() {}
    public Event(String vendorName, String eventName, int totalTickets, int ticketsSold, String status) {
        //this.vendorName = vendorName;
        this.eventName = eventName;
        this.totalTickets = totalTickets;
        this.ticketsSold = ticketsSold;
        this.status = status;
    }

    public Event(String vendorName, String eventName, int totalTickets) {

        //this.vendorName = vendorName;
        this.eventName = eventName;
        this.totalTickets = totalTickets;

    }
//    public Event(String vendorName,Long id, String eventName, String status, double ticketPrice, int ticketsSold, int totalTickets) {
//        this.id = id;
//        this.vendorName = vendorName;
//        this.eventName = eventName;
//        this.status = status;
//        this.ticketPrice = ticketPrice;
//        this.ticketsSold = ticketsSold;
//        this.totalTickets = totalTickets;
//    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getVendorName() {
//        return vendorName;
//    }
//
//    public void setVendorName(String vendorName) {
//        this.vendorName = vendorName;
//    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRemainingTickets() {
        return totalTickets - ticketsSold;
    }
}
