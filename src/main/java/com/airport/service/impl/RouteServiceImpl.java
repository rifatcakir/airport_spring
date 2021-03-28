package com.airport.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.airport.model.FlyRouteDTO;
import com.airport.model.FlyRouteLegDTO;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.repository.FlyRouteRepository;
import com.airport.persistence.repository.AirportRepository;
import com.airport.service.RouteService;
import com.airport.service.mapper.ServiceObjectMapper;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

  @Autowired
  FlyRouteRepository routeRepository;

  @Autowired
  AirportRepository airportRepository;

  @Autowired
  ServiceObjectMapper serviceObjectMapper;

  @Override
  public FlyRoute createRoute(FlyRouteDTO routeDTO) {
    verifyRouteLegs(routeDTO.getRouteLeg());
    FlyRoute entity = serviceObjectMapper.fromRouteDTOToEntity(routeDTO);
    entity.setSeatStatus(createSeatList(routeDTO.getMaxSeatNumber()));
    routeRepository.save(entity);
    return entity;
  }

  private Map<Integer, Boolean> createSeatList(int maxSeatNumber) {
    Map<Integer, Boolean> seatList = new HashMap<>();
    for (int i = 1; i <= maxSeatNumber; i++) {
      seatList.put(i, false);
    }
    return seatList;
  }

  @Override
  public FlyRoute searchRouteById(String routeId) {
    return routeRepository.findById(routeId).orElseThrow(() -> new IllegalArgumentException("Route not found!"));
  }

  private void verifyRouteLegs(FlyRouteLegDTO routeLeg) {
    if (!isLegExists(routeLeg.getArrivalAirport()) || !isLegExists(routeLeg.getDepartureAirport())) {
      throw new IllegalArgumentException("Not a valid route leg!");
    }

  }

  private boolean isLegExists(String arrivalAirport) {
    return (airportRepository.findByName(arrivalAirport) != null);
  }

  @Override
  public Set<FlyRoute> searchRouteByName(String routeName) {
    return routeRepository.findByRouteName(routeName);
  }



}
