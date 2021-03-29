package com.airport.persistence.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(indexes = {@Index(name = "INDEX_CompanyName", columnList = "companyName", unique = true)})
public class AirlineCompany {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String airlineCompanyId;

  @Column(length = 50)
  private String companyName;
  
  private Date foundationDate;
  
  private String countryName;
  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "airlineCompany")
  private Set<FlyRoute> routeList;

  public String getAirlineCompanyId() {
    return airlineCompanyId;
  }

  public void setAirlineCompanyId(String airlineId) {
    this.airlineCompanyId = airlineId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Date getFoundationDate() {
    return foundationDate;
  }

  public void setFoundationDate(Date foundationDate) {
    this.foundationDate = foundationDate;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public Set<FlyRoute> getRouteList() {
    return routeList;
  }

  public void setRouteList(Set<FlyRoute> routeList) {
    this.routeList = routeList;
  }

  



}
