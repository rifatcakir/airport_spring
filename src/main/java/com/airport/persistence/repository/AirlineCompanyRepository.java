package com.airport.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.airport.persistence.entity.AirlineCompany;

@Repository
public interface AirlineCompanyRepository extends JpaRepository<AirlineCompany, String> {

  AirlineCompany findByCompanyName(String companyName);

}
