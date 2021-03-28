package com.airport.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Ticket Buy Request")
public class TicketBuyRequest {
  
  @NotNull
  @ApiModelProperty(notes="Fly route Id",example="402881e7787583d70178758ec9160007")
  private String flyRouteId;
  
  @NotNull
  @Min(1)
  @ApiModelProperty(notes="Fly seat number",example="1")
  private int seatNumber;

  public String getFlyRouteId() {
    return flyRouteId;
  }

  public void setFlyRouteId(String flyRouteId) {
    this.flyRouteId = flyRouteId;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }
  
  
}
