package com.airport.persistence.entity;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FlyRoute {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String uuid;

  @Column(length = 150)
  private String routeName;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "airlineCompany", updatable = false, insertable = false)
  @JsonIgnore
  private AirlineCompany airlineCompany;

  @ElementCollection 
  private Map<Integer, Boolean> seatStatus;

  @Embedded
  private FlyRouteLeg routeLeg;

  public String getRouteName() {
    return routeName;
  }

  public void setRouteName(String routeName) {
    this.routeName = routeName;
  }

  public FlyRouteLeg getRouteLeg() {
    return routeLeg;
  }

  public void setRouteLeg(FlyRouteLeg routeLeg) {
    this.routeLeg = routeLeg;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public AirlineCompany getAirlineCompany() {
    return airlineCompany;
  }

  public void setAirlineCompany(AirlineCompany airlineCompany) {
    this.airlineCompany = airlineCompany;
  }

  public Map<Integer, Boolean> getSeatStatus() {
    return seatStatus;
  }

  public void setSeatStatus(Map<Integer, Boolean> seatStatus) {
    this.seatStatus = seatStatus;
  }



}
