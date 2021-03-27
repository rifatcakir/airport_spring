package com.airport.controller;

import com.airport.model.AirportDTO;
import com.airport.persistence.entity.Airport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Airport controller")
public interface AirportController {

  @ApiOperation(value = "Create Airport")
  public Airport createAirport(AirportDTO airportDTO);

  @ApiOperation(value = "Search Airport")
  public Airport searchAirportById(String id);
  

}
