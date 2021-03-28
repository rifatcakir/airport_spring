package com.airport.controller;

import java.util.Set;
import com.airport.model.RouteDTO;
import com.airport.persistence.entity.FlyRoute;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Airline Route controller")
public interface RouteController {

  @ApiOperation(value = "Creates Airport Route")
  public FlyRoute createAirportRoute(RouteDTO routeDTO);

  @ApiOperation(value = "Search Airport Route by Id")
  public FlyRoute searchAirportRouteById(String routeId);

  @ApiOperation(value = "Search Airport Route by Name")
  public Set<FlyRoute> searchAirportRouteByName(String routeName);
}
