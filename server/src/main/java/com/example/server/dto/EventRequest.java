package com.example.server.dto;

public class EventRequest {

    private String eventName;
    private String status; // Optional field if you want to set status in the request
    private double ticketPrice;
    private int totalTickets;

    public EventRequest() {}
    public EventRequest(String status, String eventName, double ticketPrice, int totalTickets) {
        this.status = status;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.totalTickets = totalTickets;
    }

    // Getters and Setters
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

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

//    @Override
//    public String toString() {
//        return "EventRequest{" +
//                "eventName='" + eventName + '\'' +
//                ", status='" + status + '\'' +
//                ", ticketPrice=" + ticketPrice +
//                ", totalTickets=" + totalTickets +
//                '}';
//    }
}
