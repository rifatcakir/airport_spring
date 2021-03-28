package com.airport.service;

import com.airport.model.AirlineCompanyDTO;
import com.airport.model.AirlineCompanyWithRouteDTO;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.persistence.repository.AirlineCompanyRepository;
import com.airport.persistence.repository.FlyRouteRepository;

public interface AirlineCompanyService {

  public AirlineCompany createAirlineCompany(AirlineCompanyDTO airlineCompany);

  public AirlineCompany searchAirlineCompanyByName(String companyName);

  public AirlineCompany assignRouteToAirlineCompany(AirlineCompanyWithRouteDTO airlineCompanyWithRoute);

  void setAirlineRepository(AirlineCompanyRepository airlineRepository);

  void setRouteRepository(FlyRouteRepository routeRepository);

}
