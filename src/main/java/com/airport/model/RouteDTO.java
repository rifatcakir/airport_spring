package com.airport.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Route informations")
public class RouteDTO {

  @NotNull
  @ApiModelProperty(notes = "Airline Route name is character sensitive.",
      example = "Sabiha Gökçen Uluslararası Havalimanı - Adnan Menderes Havalimanı")
  private String routeName;

  @NotNull
  @Valid
  @ApiModelProperty(notes = "Airline Route legs")
  private RouteLeg routeLeg;

  public String getRouteName() {
    return routeName;
  }

  public void setRouteName(String routeName) {
    this.routeName = routeName;
  }

  public RouteLeg getRouteLeg() {
    return routeLeg;
  }

  public void setRouteLeg(RouteLeg routeLeg) {
    this.routeLeg = routeLeg;
  }



}
