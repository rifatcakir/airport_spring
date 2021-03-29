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
import com.airport.model.FlyRouteDTO;
import com.airport.model.FlyRouteLegDTO;
import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.persistence.entity.Airport;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.entity.FlyRouteLeg;
import com.airport.persistence.entity.Ticket;

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
    mockCompany.setAirlineCompanyId("1");
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
    Map<Integer, Boolean> seatSample = new HashMap<>();
    for (int i = 1; i <= 100; i++) {
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
    entity.setAirportId("1");
    entity.setCityName("İstanbul");
    entity.setCountryName("Turkey");
    entity.setFoundationDate(new Date());
    entity.setName("Sabiha Gökçen HAVALİMANI");
    return entity;
  }

  public FlyRouteDTO createMockFlyRouteDTO() {
    FlyRouteDTO dto = new FlyRouteDTO();
    dto.setMaxSeatNumber(100);
    dto.setRouteLeg(createRouteLegDTO());
    dto.setRouteName("Sabiha Gökçecen - Adnan menderes");
    dto.setTicketPrice(BigDecimal.valueOf(100));
    return dto;
  }

  private FlyRouteLegDTO createRouteLegDTO() {
    FlyRouteLegDTO dto = new FlyRouteLegDTO();
    dto.setArrivalAirport("A");
    dto.setArrivalDateTime(new Date());
    dto.setDepartureAirport("B");
    dto.setDepartureDateTime(new Date());
    return dto;
  }

  public Airport createMockAirport(String arrivalPort) {
    Airport port = new Airport();
    port.setName(arrivalPort);
    port.setAirportId("1");
    return port;
  }

  public TicketBuyRequest createMockTicketBuyRequest() {
    TicketBuyRequest request = new TicketBuyRequest();
    request.setCreditCardNo("1234-5678-9101-1121");
    request.setFlyRouteId("1");
    request.setSeatNumber(1);
    request.setTicketPrice(BigDecimal.valueOf(100));
    return request;
  }

  public Ticket createMockTicketEntity() {
    Ticket entity = new Ticket();
    entity.setCreditCardNumber("123456******1112");
    entity.setFlyRoute(createMockFlyRoute());
    entity.getFlyRoute().setFlyRouteId("1");
    entity.setSeatNumber(1);
    entity.setTicketPrice(BigDecimal.valueOf(100));
    entity.setTicketId("1");
    return entity;
  }
}
