package com.airport.model;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Airline with Route information")
public class AirlineCompanyWithRouteDTO {

  @NotNull
  @ApiModelProperty(notes = "Company Id", example = "402881e7787583d70178758ec9160007")
  private String airlineCompanyId;

  @NotNull
  @ApiModelProperty(notes = "Airline Route Id", example = "402881e7787583d70178758ee2d90008")
  private String routeId;

  public String getAirlineCompanyId() {
    return airlineCompanyId;
  }

  public void setAirlineCompanyId(String airlineCompanyId) {
    this.airlineCompanyId = airlineCompanyId;
  }

  public String getRouteId() {
    return routeId;
  }

  public void setRouteId(String routeId) {
    this.routeId = routeId;
  }

}
