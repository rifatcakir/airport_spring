package com.airport.persistence.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.airport.persistence.entity.FlyRoute;

@Repository
public interface FlyRouteRepository extends JpaRepository<FlyRoute, String> {

  Set<FlyRoute> findByRouteName(String name);

}
