package com.airport.service.impl;

import java.math.BigDecimal;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.entity.Ticket;
import com.airport.persistence.repository.FlyRouteRepository;
import com.airport.persistence.repository.TicketRepository;
import com.airport.service.TicketService;
import com.airport.service.component.FlyPriceUtility;
import com.airport.service.component.CreditCardUtility;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

  @Autowired
  FlyRouteRepository routeRepository;

  @Autowired
  TicketRepository ticketRepository;

  @Autowired
  CreditCardUtility creditCardUtility;

  @Autowired
  FlyPriceUtility flyPriceUtil;

  @Override
  public Ticket buyTicket(TicketBuyRequest buyRequest) {
    FlyRoute route = routeRepository.findById(buyRequest.getFlyRouteId()).orElseThrow(() -> new IllegalArgumentException("Route not found!"));

    arrangeSeat(route, buyRequest);

    Ticket newTicket = new Ticket();
    newTicket.setTicketPrice(verifyAmount(buyRequest, route));
    newTicket.setCreditCardNumber(creditCardUtility.maskCCN(buyRequest.getCreditCardNumber()));
    newTicket.setFlyRoute(route);
    newTicket.setSeatNumber(buyRequest.getSeatNumber());
    flyPriceUtil.checkForIncreaseTicketPrice(route);
    return ticketRepository.save(newTicket);
  }



  @Override
  public Ticket searchTicketById(String ticketId) {
    return ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found!"));
  }


  @Override
  public Ticket cancelTicketById(String ticketId) {
    Ticket ticket = searchTicketById(ticketId);
    if (ticket != null) {
      relaseSeat(ticket);
      ticket.setFlyRoute(null);
    }
    return ticket;
  }

  private void relaseSeat(Ticket ticket) {
    ticket.getFlyRoute().getSeatStatus().put(ticket.getSeatNumber(), false);
  }



  private BigDecimal verifyAmount(TicketBuyRequest buyRequest, FlyRoute flyRoute) {
    if (buyRequest.getTicketPrice().compareTo(flyRoute.getTicketPrice()) != 0) {
      throw new IllegalArgumentException("Price is not matching!");
    }
    return buyRequest.getTicketPrice();
  }


  private void arrangeSeat(FlyRoute flyRoute, TicketBuyRequest buyRequest) {
    if (!isSeatFree(flyRoute, buyRequest)) {
      throw new IllegalArgumentException("Seat is not empty!");
    }
    flyRoute.getSeatStatus().put(buyRequest.getSeatNumber(), true);
  }


  private boolean isSeatFree(FlyRoute flyRoute, TicketBuyRequest buyRequest) {
    if (flyRoute.getSeatStatus().size() < buyRequest.getSeatNumber()) {
      throw new IllegalArgumentException("Ticket buy denied! Ticket number out of range!");
    }
    return !flyRoute.getSeatStatus().get(buyRequest.getSeatNumber());
  }


  @Override
  public void setRouteRepository(FlyRouteRepository routeRepository) {
    this.routeRepository = routeRepository;
  }


  @Override
  public void setTicketRepository(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

}
