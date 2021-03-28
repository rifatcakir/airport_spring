package com.airport.service;

import com.airport.model.AirportDTO;
import com.airport.persistence.entity.Airport;
import com.airport.persistence.repository.AirportRepository;

public interface AirportService {

  public Airport createAirPort(AirportDTO airportDTO);

  public Airport searchAirport(String airportName);

  void setAirportRepository(AirportRepository airportRepository);

}
