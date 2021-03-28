package com.airport.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.airport.persistence.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>{

}
