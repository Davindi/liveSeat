package org.example;

import java.util.Scanner;

public class LiveSeatCLI {
    private static Vendor vendor;
    private static customer customer;
    private static LiveSeatTicketsPool ticketManager;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configuration Setup
        System.out.println("Welcome to the LiveSeat CLI!");

        LiveSeatConfiguration config = setupConfiguration(scanner);

        // Initialize the ticket manager
        ticketManager = new LiveSeatTicketsPool(config.getTotalTickets(), config.getMaxTicketCapacity());

        // Command Handling
        String command;
        System.out.println("Type 'start' to begin ticket operations, 'stop' to end, or 'exit' to quit.");
        while (true) {
            command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "start":
                    startTicketHandling(config);
                    break;
                case "stop":
                    stopTicketHandling();
                    break;
                case "exit":
                    stopTicketHandling();
                    System.out.println("Exiting the Ticketing System CLI.");
                    return;
                default:
                    System.out.println("Unknown command. Type 'start', 'stop', or 'exit'.");
            }
        }
    }

    private static LiveSeatConfiguration setupConfiguration(Scanner scanner) {
        System.out.print("Enter Total Number of Tickets: ");
        int totalTickets = getIntInput(scanner);

        System.out.print("Enter Ticket Release Rate (tickets per interval): ");
        int ticketReleaseRate = getIntInput(scanner);

        System.out.print("Enter Customer Retrieval Rate (tickets per interval): ");
        int customerRetrievalRate = getIntInput(scanner);

        System.out.print("Enter Maximum Ticket Capacity: ");
        int maxTicketCapacity = getIntInput(scanner);

        return new LiveSeatConfiguration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    private static int getIntInput(Scanner scanner) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input <= 0) {
                    System.out.println("Please enter a positive number.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return input;
    }

    private static void startTicketHandling(LiveSeatConfiguration config) {
        if (vendor == null || !vendor.isAlive()) {
            vendor = new Vendor(ticketManager, config.getTicketReleaseRate());
            vendor.start();
            System.out.println("Ticket production started.");
        }

        if (customer == null || !customer.isAlive()) {
            customer = new customer(ticketManager, config.getCustomerRetrievalRate());
            customer.start();
            System.out.println("Ticket consumption started.");
        }
    }

    private static void stopTicketHandling() {
        if (vendor != null && vendor.isAlive()) {
            vendor.interrupt();
            System.out.println("Ticket production stopped.");
        }

        if (customer != null && customer.isAlive()) {
            customer.interrupt();
            System.out.println("Ticket consumption stopped.");
        }
    }
}
