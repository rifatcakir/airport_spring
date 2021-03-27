package com.airport.controller.impl;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.airport.controller.AirlineCompanyController;
import com.airport.model.AirlineCompanyDTO;
import com.airport.model.AirlineCompanyWithRouteDTO;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.service.AirlineCompanyService;

@RestController
@RequestMapping("/airline/company")
public class AirlineCompanyControllerImpl implements AirlineCompanyController {

  @Autowired
  AirlineCompanyService airlineCompanyService;

  @Override
  @PostMapping("/create")
  public AirlineCompany createAirlineCompany(@RequestBody @Valid AirlineCompanyDTO company) {
    return airlineCompanyService.createAirlineCompany(company);
  }

  @Override
  @GetMapping("/search")
  public AirlineCompany searchAirlineCompanyByName(@RequestParam("companyName") String companyName) {
    return airlineCompanyService.searchAirlineCompanyByName(companyName);
  }

  @Override
  @PostMapping("assign/route")
  public AirlineCompany addRouteForAirlineCompany(@RequestBody @Valid AirlineCompanyWithRouteDTO airlineCompanyWithRoute) {
    return airlineCompanyService.assignRouteToAirlineCompany(airlineCompanyWithRoute);
  }



}
