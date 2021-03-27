package com.airport.persistence.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FlyRouteLeg {

  @Column(length = 50)
  private String departureAirport;

  @Column(length = 50)
  private String arrivalAirport;

  private Date departureDateTime;

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
