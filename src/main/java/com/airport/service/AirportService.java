package com.airport.service;

import com.airport.model.AirportDTO;
import com.airport.persistence.entity.Airport;

public interface AirportService {

  public Airport createAirPort(AirportDTO airportDTO);

  public Airport searchAirport(String airportName);

}
