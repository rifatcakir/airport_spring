package com.airport.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Airline company informations")
public class AirlineCompanyDTO {

  @NotNull
  @ApiModelProperty(notes = "Company name is character sensitive.", example = "THY")
  private String companyName;

  @NotNull
  @ApiModelProperty(notes = "Airline foundation date", example = "18.07.1933")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
  private Date foundationDate;

  @NotNull
  @ApiModelProperty(notes = "The country where center of the airline", example = "Turkey")
  private String countryName;

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



}
