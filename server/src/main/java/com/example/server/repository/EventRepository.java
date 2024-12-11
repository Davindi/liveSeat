package com.example.server.repository;

import com.example.server.dto.EventTicket;
import com.example.server.models.Event;
import com.example.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByEventName(String eventName);

    List<Event> findByUser_Id(int vendorId);
    @Query("SELECT e  FROM Event e JOIN Ticket t ON e.id = t.event.id WHERE t.customer.id = :customerId")
    List<Event> findPurchasedTicketsByCustomerId(@Param("customerId") int customerId);
    @Query("SELECT new com.example.server.dto.EventTicket(e, t.quantity) " +
            "FROM Event e JOIN Ticket t ON e.id = t.event.id " +
            "WHERE t.customer.id = :customerId")
    List<EventTicket> findPurchasedTicketsWithQuantityByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT e FROM Event e WHERE e.status = 'Active'")
    List<Event> findAllActiveEvents();
//    List<Event> findByVendorId(@Param("vendorId") int vendor_id);
//
//    List<Event> findByVendor(User vendor);
//    List<Event> findByStatus(String status);
}
