package com.airport.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.airport.model.AirportDTO;
import com.airport.persistence.entity.Airport;
import com.airport.persistence.repository.AirportRepository;
import com.airport.service.AirportService;
import com.airport.service.mapper.ServiceObjectMapper;

@Service
@Transactional
public class AirportServiceImpl implements AirportService {

  @Autowired
  AirportRepository airportRepository;

  @Autowired
  ServiceObjectMapper serviceObjectMapper;

  @Override
  public Airport createAirPort(AirportDTO airportDTO) {
    return airportRepository.save(serviceObjectMapper.airportDTOToEntity(airportDTO));
  }

  @Override
  public Airport searchAirport(String name) {
    Airport searchResult = airportRepository.findByName(name);
    if (searchResult == null) {
      throw new IllegalArgumentException("Airport not found!");
    }
    return searchResult;
  }



}
