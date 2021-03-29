package com.airport.service;

import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.Ticket;
import com.airport.persistence.repository.FlyRouteRepository;
import com.airport.persistence.repository.TicketRepository;

public interface TicketService {

  public Ticket buyTicket(TicketBuyRequest buyRequest);

  public Ticket searchTicketById(String ticketId);

  public Ticket cancelTicketById(String ticketId);

  void setRouteRepository(FlyRouteRepository routeRepository);

  void setTicketRepository(TicketRepository ticketRepository);

}
