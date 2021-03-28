package com.airport.controller;

import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.Ticket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Ticket controller")
public interface TicketController {

  @ApiOperation(value = "Buy ticket")
  public Ticket buyTicket(TicketBuyRequest buyRequest);

  @ApiOperation(value = "Search ticket by Id")
  public Ticket searchTicketById(String ticketId);

  @ApiOperation(value = "Cancel ticket by Id")
  public Ticket cancelTicketById(String ticketId);

}
