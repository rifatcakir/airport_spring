package com.airport.controller;

import com.airport.model.AirlineCompanyDTO;
import com.airport.model.AirlineCompanyWithRouteDTO;
import com.airport.persistence.entity.AirlineCompany;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "Airline Company controller")
public interface AirlineCompanyController {

  @ApiOperation(value = "Create Airline Company")
  public AirlineCompany createAirlineCompany(AirlineCompanyDTO company);

  @ApiOperation(value = "Search Airline Company By Company Name")
  public AirlineCompany searchAirlineCompanyByName(String companyName);

  @ApiOperation(value = "Add Route into Airline Company")
  public AirlineCompany addRouteForAirlineCompany(AirlineCompanyWithRouteDTO airlineCompanyWithRoute);
  

}
