package com.airport.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Ticket Buy Request")
public class TicketBuyRequest {

  @NotNull
  @ApiModelProperty(notes = "Fly route Id", example = "402881e7787583d70178758ec9160007")
  private String flyRouteId;

  @NotNull
  @Min(1)
  @ApiModelProperty(notes = "Fly seat number", example = "1")
  private int seatNumber;

  @NotNull
  @Size(min = 16)
  @ApiModelProperty(notes = "Credit Card number", example = "1234-5678-9101-1121")
  private String creditCardNo;

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

  public String getCreditCardNo() {
    return creditCardNo;
  }

  public void setCreditCardNo(String creditCardNo) {
    this.creditCardNo = creditCardNo;
  }

}
