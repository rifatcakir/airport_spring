package com.airport.service;

import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.Ticket;

public interface TicketService {

  public Ticket buyTicket(TicketBuyRequest buyRequest);

  public Ticket searchTicketById(String ticketId);

  public Ticket cancelTicketById(String ticketId);

}
