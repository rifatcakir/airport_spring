package com.airport.model;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Route informations")
public class FlyRouteDTO {

  @NotNull
  @ApiModelProperty(notes = "Airline Route name is character sensitive.",
      example = "Sabiha Gökçen Uluslararası Havalimanı - Adnan Menderes Havalimanı")
  private String routeName;

  @NotNull
  @Valid
  @ApiModelProperty(notes = "Airline Route legs")
  private FlyRouteLegDTO routeLeg;

  @NotNull
  @Valid
  @Min(1)
  @ApiModelProperty(notes = "Max number of seats", example = "100")
  private int maxSeatNumber;

  @NotNull
  @ApiModelProperty(notes = "Ticket price", example = "100")
  private BigDecimal ticketPrice;

  public String getRouteName() {
    return routeName;
  }

  public void setRouteName(String routeName) {
    this.routeName = routeName;
  }

  public FlyRouteLegDTO getRouteLeg() {
    return routeLeg;
  }

  public void setRouteLeg(FlyRouteLegDTO routeLeg) {
    this.routeLeg = routeLeg;
  }

  public int getMaxSeatNumber() {
    return maxSeatNumber;
  }

  public void setMaxSeatNumber(int maxSeatNumber) {
    this.maxSeatNumber = maxSeatNumber;
  }

  public BigDecimal getTicketPrice() {
    return ticketPrice;
  }

  public void setTicketPrice(BigDecimal ticketPrice) {
    this.ticketPrice = ticketPrice;
  }

}
