package com.airport.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import com.airport.model.AirlineCompanyDTO;
import com.airport.model.FlyRouteDTO;
import com.airport.model.AirportDTO;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.entity.FlyRouteLeg;
import com.airport.persistence.entity.Airport;

@Component
public class ServiceObjectMapper {

  public AirlineCompany airlineCompanyDTOToEntity(AirlineCompanyDTO airlineCompanyDTO) {
    AirlineCompany entity = new AirlineCompany();
    BeanUtils.copyProperties(airlineCompanyDTO, entity);
    return entity;
  }

  public Airport airportDTOToEntity(AirportDTO dto) {
    Airport entity = new Airport();
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }

  public FlyRoute fromRouteDTOToEntity(FlyRouteDTO dto) {
    FlyRoute entity = new FlyRoute();
    BeanUtils.copyProperties(dto, entity);
    FlyRouteLeg routeLeg = new FlyRouteLeg();
    BeanUtils.copyProperties(dto.getRouteLeg(), routeLeg);
    entity.setRouteLeg(routeLeg);
    return entity;
  }



}
