package com.airport.service.impl;

import java.util.Optional;
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
    Optional<AirlineCompany> company = airlineRepository.findById(airlineCompanyWithRoute.getAirlineCompanyId());
    if (company.isPresent()) {
      Optional<FlyRoute> route = routeRepository.findById(airlineCompanyWithRoute.getRouteId());
      if (route.isPresent()) {
        company.get().getRouteList().add(route.get());
        airlineRepository.save(company.get());
        return company.get();
      }
    }
    throw new IllegalArgumentException("Not a valid argument!");
  }

}
