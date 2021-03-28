package com.airport.controller.impl;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.airport.controller.AirportController;
import com.airport.model.AirportDTO;
import com.airport.persistence.entity.Airport;
import com.airport.service.AirportService;

@RestController
@RequestMapping("/airport")
public class AirportControllerImpl implements AirportController {

  @Autowired
  AirportService airportService;

  @Override
  @PostMapping("/create")
  public Airport createAirport(@RequestBody @Valid AirportDTO airportDTO) {
    return airportService.createAirPort(airportDTO);
  }

  @Override
  @GetMapping("/search")
  public Airport searchAirportByName(@RequestParam("name") String name) {
    return airportService.searchAirport(name);
  }
}
