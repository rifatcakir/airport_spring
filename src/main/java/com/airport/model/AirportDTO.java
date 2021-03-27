package com.airport.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "Airport informations")
public class AirportDTO {

  @NotNull
  @ApiModelProperty(notes = "Airport name is character sensitive.", example = "Sabiha Gökçen Uluslararası Havalimanı")
  private String name;

  @NotNull
  @ApiModelProperty(notes = "Airline foundation date", example = "18.07.1933")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
  private Date foundationDate;

  @NotNull
  @ApiModelProperty(notes = "The country where center of the airline", example = "Turkey")
  private String countryName;

  @NotNull
  @ApiModelProperty(notes = "The city where airline company is", example = "Istanbul")
  private String cityName;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

}
