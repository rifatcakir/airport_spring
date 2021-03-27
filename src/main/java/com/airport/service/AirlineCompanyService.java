package com.airport.service;

import com.airport.model.AirlineCompanyDTO;
import com.airport.model.AirlineCompanyWithRouteDTO;
import com.airport.persistence.entity.AirlineCompany;

public interface AirlineCompanyService {

  public AirlineCompany createAirlineCompany(AirlineCompanyDTO airlineCompany);

  public AirlineCompany searchAirlineCompanyByName(String companyName);

  public AirlineCompany assignRouteToAirlineCompany(AirlineCompanyWithRouteDTO airlineCompanyWithRoute);

}
