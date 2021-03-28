package com.airport.service.impl;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.entity.Ticket;
import com.airport.persistence.repository.FlyRouteRepository;
import com.airport.persistence.repository.TicketRepository;
import com.airport.service.TicketService;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

  @Autowired
  FlyRouteRepository routeRepository;

  @Autowired
  TicketRepository ticketRepository;

  @Override
  public Ticket buyTicket(TicketBuyRequest buyRequest) {
    Optional<FlyRoute> route = routeRepository.findById(buyRequest.getFlyRouteId());
    if (route.isPresent() && isSeatFree(route.get(), buyRequest)) {
      arrangeSeat(route.get(), buyRequest);
      Ticket newTicket = new Ticket();
      newTicket.setFlyRoute(route.get());
      newTicket.setSeatNumber(buyRequest.getSeatNumber());
      return ticketRepository.save(newTicket);
    }
    throw new IllegalArgumentException("Ticket buy denied!");
  }


  private void arrangeSeat(FlyRoute flyRoute, TicketBuyRequest buyRequest) {
    flyRoute.getSeatStatus().put(buyRequest.getSeatNumber(), true);
  }


  private boolean isSeatFree(FlyRoute flyRoute, TicketBuyRequest buyRequest) {
    if (flyRoute.getSeatStatus().size() < buyRequest.getSeatNumber()) {
      throw new IllegalArgumentException("Ticket buy denied! Ticket number out of range!");
    }
    return !flyRoute.getSeatStatus().get(buyRequest.getSeatNumber());
  }



}
