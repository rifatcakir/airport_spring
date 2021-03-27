package com.airport.controller.impl;

import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.airport.controller.RouteController;
import com.airport.model.RouteDTO;
import com.airport.persistence.entity.FlyRoute;
import com.airport.service.RouteService;

@RestController
@RequestMapping("/airline/route")
public class RouteControllerImpl implements RouteController {

  @Autowired
  RouteService routeService;


  @Override
  @PostMapping("/create")
  public FlyRoute createAirportRoute(@RequestBody @Valid RouteDTO routeDTO) {
    return routeService.createRoute(routeDTO);
  }

  @Override
  @GetMapping("/search/Id")
  public FlyRoute searchAirportRouteById(@RequestParam("routeId") String routeId) {
    return routeService.searchRouteById(routeId);
  }

  @Override
  @GetMapping("/search/routeName")
  public Set<FlyRoute> searchAirportRouteByName(@RequestParam("routeName") String routeName) {
    return routeService.searchRouteByName(routeName);
  }

}
