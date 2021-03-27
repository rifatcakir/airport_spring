package com.airport.service;

import java.util.Set;
import com.airport.model.RouteDTO;
import com.airport.persistence.entity.FlyRoute;

public interface RouteService {

  public FlyRoute createRoute(RouteDTO routeDTO);

  public FlyRoute searchRouteById(String name);

  public Set<FlyRoute> searchRouteByName(String routeName);
}
