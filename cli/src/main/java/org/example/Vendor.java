package org.example;

public class Vendor extends Thread{
    private final LiveSeatTicketsPool ticketManager;
    private final int rate;

    public Vendor(LiveSeatTicketsPool ticketManager, int rate) {
        this.ticketManager = ticketManager;
        this.rate = rate;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            ticketManager.produce(rate);
            try {
                Thread.sleep(5000); // Produce tickets every 2 seconds
            } catch (InterruptedException e) {
                System.out.println("Vendor interrupted.");
                break;
            }
        }
    }
}
