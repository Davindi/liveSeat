package org.example;

public class customer extends Thread {
    private final LiveSeatTicketsPool ticketManager;
    private final int rate;

    public customer(LiveSeatTicketsPool ticketManager, int rate) {
        this.ticketManager = ticketManager;
        this.rate = rate;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            ticketManager.consume(rate);
            try {
                Thread.sleep(7000); // Consume tickets every 3 seconds
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted.");
                break;
            }
        }
    }
}
