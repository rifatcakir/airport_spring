package com.airport.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.airport.persistence.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, String> {

  Airport findByName(String airportName);

  
}
