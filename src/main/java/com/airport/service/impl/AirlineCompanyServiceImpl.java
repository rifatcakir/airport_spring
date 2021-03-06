package com.airport.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.airport.model.AirlineCompanyDTO;
import com.airport.model.AirlineCompanyWithRouteDTO;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.repository.AirlineCompanyRepository;
import com.airport.persistence.repository.FlyRouteRepository;
import com.airport.service.AirlineCompanyService;
import com.airport.service.mapper.ServiceObjectMapper;

@Service
@Transactional
public class AirlineCompanyServiceImpl implements AirlineCompanyService {

  @Autowired
  AirlineCompanyRepository airlineRepository;

  @Autowired
  ServiceObjectMapper serviceObjectMapper;

  @Autowired
  FlyRouteRepository routeRepository;

  @Override
  public AirlineCompany createAirlineCompany(AirlineCompanyDTO airlineCompany) {
    return airlineRepository.save(serviceObjectMapper.airlineCompanyDTOToEntity(airlineCompany));
  }

  @Override
  public AirlineCompany searchAirlineCompanyByName(String companyName) {
    AirlineCompany searchResult = airlineRepository.findByCompanyName(companyName);
    if (searchResult == null) {
      throw new IllegalArgumentException("Airline company not found!");
    }
    return searchResult;
  }

  @Override
  public AirlineCompany assignRouteToAirlineCompany(AirlineCompanyWithRouteDTO airlineCompanyWithRoute) {
    AirlineCompany company = airlineRepository.findById(airlineCompanyWithRoute.getAirlineCompanyId())
        .orElseThrow(() -> new IllegalArgumentException("Company not found!"));
    FlyRoute route =
        routeRepository.findById(airlineCompanyWithRoute.getRouteId()).orElseThrow(() -> new IllegalArgumentException("Route not found!"));

    company.getRouteList().add(route);
    airlineRepository.save(company);
    return company;
  }

  @Override
  public void setAirlineRepository(AirlineCompanyRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }

  @Override
  public void setRouteRepository(FlyRouteRepository routeRepository) {
    this.routeRepository = routeRepository;
  }

}
