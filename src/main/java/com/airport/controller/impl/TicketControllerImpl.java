package com.airport.controller.impl;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.airport.controller.TicketController;
import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.Ticket;
import com.airport.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketControllerImpl implements TicketController {

  @Autowired
  TicketService ticketService;

  @Override
  @PostMapping("/buy")
  public Ticket buyTicket(@RequestBody @Valid TicketBuyRequest buyRequest) {
    return ticketService.buyTicket(buyRequest);
  }

  @Override
  @GetMapping("/search/Id")
  public Ticket searchTicketById(@RequestParam("ticketId") String ticketId) {
    return ticketService.searchTicketById(ticketId);
  }

  @Override
  @GetMapping("/cancel/Id")
  public Ticket cancelTicketById(@RequestParam("ticketId") String ticketId) {
    return ticketService.cancelTicketById(ticketId);
  }

}
