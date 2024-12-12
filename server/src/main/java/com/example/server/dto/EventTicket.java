package com.example.server.dto;

import com.example.server.models.Event;

public class EventTicket {
    private Event event;
    private int quantity;

    public EventTicket(Event event, int quantity) {
        this.event = event;
        this.quantity = quantity;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
