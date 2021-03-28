package com.airport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.airport.model.AirlineCompanyDTO;
import com.airport.model.AirlineCompanyWithRouteDTO;
import com.airport.model.AirportDTO;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.persistence.entity.Airport;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.entity.FlyRouteLeg;

@Component
@Profile("unit-test")
public class MocDataUtil {

  public AirlineCompanyDTO createMockAirlineCompanyRequest() {
    AirlineCompanyDTO mockCompany = new AirlineCompanyDTO();
    mockCompany.setCompanyName("THY");
    mockCompany.setCountryName("Turkey");
    mockCompany.setFoundationDate(new Date());
    return mockCompany;
  }

  public AirlineCompany createMockAirlineCompanyEntity() {
    AirlineCompany mockCompany = new AirlineCompany();
    mockCompany.setCompanyName("THY");
    mockCompany.setCountryName("Turkey");
    mockCompany.setFoundationDate(new Date());
    mockCompany.setRouteList(new HashSet<>());
    mockCompany.setUuid("1");
    return mockCompany;
  }

  public AirlineCompanyWithRouteDTO createAirlineCompanyWithRouteDTO() {
    AirlineCompanyWithRouteDTO dto = new AirlineCompanyWithRouteDTO();
    dto.setAirlineCompanyId("1");
    dto.setRouteId("1");
    return dto;
  }

  public FlyRoute createMockFlyRoute() {
    FlyRoute entity = new FlyRoute();
    entity.setAirlineCompany(createMockAirlineCompanyEntity());
    entity.setRouteName("Test");
    entity.setTicketPrice(BigDecimal.valueOf(100));
    entity.setUuid("1");
    Map<Integer, Boolean> seatSample = new HashMap<>();
    for (int i = 1; i < 11; i++) {
      seatSample.put(i, false);
    }
    entity.setSeatStatus(seatSample);

    entity.setRouteLeg(createMockFlyRouteLeg());
    return entity;
  }

  public FlyRouteLeg createMockFlyRouteLeg() {
    FlyRouteLeg leg = new FlyRouteLeg();
    leg.setArrivalAirport("A");
    leg.setArrivalDateTime(new Date());
    leg.setDepartureAirport("B");
    leg.setDepartureDateTime(new Date());
    return leg;
  }

  public AirportDTO createMockAirportDTO() {
    AirportDTO dto = new AirportDTO();
    dto.setCityName("İstanbul");
    dto.setCountryName("Turkey");
    dto.setFoundationDate(new Date());
    dto.setName("Sabiha Gökçen HAVALİMANI");
    return dto;
  }

  public Airport createAirportEntity() {
    Airport entity = new Airport();
    entity.setUuid("1");
    entity.setCityName("İstanbul");
    entity.setCountryName("Turkey");
    entity.setFoundationDate(new Date());
    entity.setName("Sabiha Gökçen HAVALİMANI");
    return entity;
  }
}
