package com.airport.persistence.entity;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Ticket {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String ticketId;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = {"seatStatus", "ticketPrice"})
  private FlyRoute flyRoute;

  private int seatNumber;

  private String creditCardNumber;

  private BigDecimal ticketPrice;

  public String getTicketId() {
    return ticketId;
  }

  public void setTicketId(String ticketId) {
    this.ticketId = ticketId;
  }

  public FlyRoute getFlyRoute() {
    return flyRoute;
  }

  public void setFlyRoute(FlyRoute flyRoute) {
    this.flyRoute = flyRoute;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public BigDecimal getTicketPrice() {
    return ticketPrice;
  }

  public void setTicketPrice(BigDecimal ticketPrice) {
    this.ticketPrice = ticketPrice;
  }


}
