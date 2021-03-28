package com.airport.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import com.airport.model.AirlineCompanyDTO;
import com.airport.model.FlyRouteDTO;
import com.airport.model.FlyRouteLegDTO;
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

  public AirlineCompanyDTO airlineCompanyEntityToDTO(AirlineCompany airlineCompanyEntity) {
    AirlineCompanyDTO dto = new AirlineCompanyDTO();
    BeanUtils.copyProperties(airlineCompanyEntity, dto);
    return dto;
  }

  public Airport airportDTOToEntity(AirportDTO dto) {
    Airport entity = new Airport();
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }

  public AirportDTO airportEntityToDTO(Airport entity) {
    AirportDTO dto = new AirportDTO();
    BeanUtils.copyProperties(entity, dto);
    return dto;
  }

  public FlyRoute fromRouteDTOToEntity(FlyRouteDTO dto) {
    FlyRoute entity = new FlyRoute();
    BeanUtils.copyProperties(dto, entity);
    FlyRouteLeg routeLeg = new FlyRouteLeg();
    BeanUtils.copyProperties(dto.getRouteLeg(), routeLeg);
    entity.setRouteLeg(routeLeg);
    return entity;
  }

  public FlyRouteDTO fromRouteEntityToDTO(FlyRoute entity) {
    FlyRouteDTO dto = new FlyRouteDTO();
    BeanUtils.copyProperties(entity, dto);
    FlyRouteLegDTO routeLeg = new FlyRouteLegDTO();
    BeanUtils.copyProperties(entity.getRouteLeg(), routeLeg);
    dto.setRouteLeg(routeLeg);
    return dto;
  }

}
