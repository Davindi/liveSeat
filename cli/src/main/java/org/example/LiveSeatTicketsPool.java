package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class LiveSeatTicketsPool {
    private int ticketsAvailable;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public LiveSeatTicketsPool(int initialTickets, int maxCapacity) {
        this.ticketsAvailable = initialTickets;
        this.maxCapacity = maxCapacity;
        logStatus("Initial tickets: " + initialTickets);
    }

    public void produce(int tickets) {
        synchronized (this) {
            if (ticketsAvailable + tickets <= maxCapacity) {
                ticketsAvailable += tickets;
                logTransaction("release", tickets);
            } else {
                System.out.println("Cannot release " + tickets + " tickets. Exceeds max capacity.");
            }
        }
    }

    public void consume(int tickets) {
        synchronized (this) {
            if (ticketsAvailable >= tickets) {
                ticketsAvailable -= tickets;
                logTransaction("buy", tickets);
            } else {
                System.out.println("Not enough tickets to Buy. Tickets available: " + ticketsAvailable);
            }
        }
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    private void logTransaction(String action, int tickets) {
        String timestamp = formatter.format(new Date());
        System.out.println("[" + timestamp + "] " + action + " " + tickets + " tickets. Tickets available: " + ticketsAvailable);
    }

    private void logStatus(String message) {
        String timestamp = formatter.format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

}
