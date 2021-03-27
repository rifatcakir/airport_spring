package com.airport.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Airline Route leg information")
public class RouteLeg {

  @NotNull
  @ApiModelProperty(notes = "Airport name is character sensitive.", example = "Sabiha Gökçen Uluslararası Havalimanı")
  private String departureAirport;

  @NotNull
  @ApiModelProperty(notes = "Airport name is character sensitive.", example = "Adnan Menderes Havalimanı")
  private String arrivalAirport;

  @NotNull
  @ApiModelProperty(notes = "Departure Date", example = "20.05.2021 12:00:00")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
  private Date departureDateTime;

  @NotNull
  @ApiModelProperty(notes = "Arrival Date", example = "20.05.2021 13:00:00")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
  private Date arrivalDateTime;

  public String getDepartureAirport() {
    return departureAirport;
  }

  public void setDepartureAirport(String departureAirport) {
    this.departureAirport = departureAirport;
  }

  public String getArrivalAirport() {
    return arrivalAirport;
  }

  public void setArrivalAirport(String arrivalAirport) {
    this.arrivalAirport = arrivalAirport;
  }

  public Date getDepartureDateTime() {
    return departureDateTime;
  }

  public void setDepartureDateTime(Date departureDateTime) {
    this.departureDateTime = departureDateTime;
  }

  public Date getArrivalDateTime() {
    return arrivalDateTime;
  }

  public void setArrivalDateTime(Date arrivalDateTime) {
    this.arrivalDateTime = arrivalDateTime;
  }


}
