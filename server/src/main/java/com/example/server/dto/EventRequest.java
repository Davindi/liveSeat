package com.example.server.dto;

public class EventRequest {
    private String vendorName;
    //private int vendorId;
    private String eventName;
    private String status; // Optional field if you want to set status in the request
    private double ticketPrice;
    private int totalTickets;
    private int ticketsSold;


    // Getters and Setters
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

//    public int getVendorId() {
//        return vendorId;
//    }
//
//    public void setVendorId(int vendorId) {
//        this.vendorId = vendorId;
//    }

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

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    @Override
    public String toString() {
        return "EventRequest{" +
                "vendorName='" + vendorName + '\'' +

                ", eventName='" + eventName + '\'' +
                ", status='" + status + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", totalTickets=" + totalTickets +
                ", ticketsSold=" + ticketsSold +
                '}';
    }
}
