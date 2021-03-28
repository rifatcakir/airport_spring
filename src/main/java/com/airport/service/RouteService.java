package com.airport.service;

import java.util.Set;
import com.airport.model.FlyRouteDTO;
import com.airport.persistence.entity.FlyRoute;

public interface RouteService {

  public FlyRoute createRoute(FlyRouteDTO routeDTO);

  public FlyRoute searchRouteById(String name);

  public Set<FlyRoute> searchRouteByName(String routeName);
}
