package com.airport.custom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.util.Set;
import org.assertj.core.api.AbstractAssert;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.entity.FlyRouteLeg;

public class CustomAirlineCompanyWithRouteAssert extends AbstractAssert<CustomAirlineCompanyWithRouteAssert, AirlineCompany> {

  public CustomAirlineCompanyWithRouteAssert(AirlineCompany actual, Class<?> selfType) {
    super(actual, selfType);
    isNotNull();
  }

  public CustomAirlineCompanyWithRouteAssert hasValidCompanyInformations() {
    AirlineCompany company = actual;
    assertThat(company).isNotNull();
    assertThat(company.getCompanyName()).isNotNull();
    assertThat(company.getCountryName()).isNotNull();
    assertThat(company.getFoundationDate()).isNotNull();
    assertThat(company.getUuid()).isNotNull();
    return myself;
  }

  public CustomAirlineCompanyWithRouteAssert hasValidRouteList() {
    Set<FlyRoute> routeList = actual.getRouteList();
    assertThat(routeList).isNotNull();
    if (!routeList.isEmpty()) {
      for (FlyRoute route : routeList) {
        assertThat(route.getAirlineCompany()).isNotNull();
        assertThat(route.getRouteName()).isNotNull();
        assertThat(route.getSeatStatus()).isNotNull();
        assertThat(route.getUuid()).isNotNull();
        assertThat(route.getTicketPrice()).isNotNegative();
        assertTrue(route.getTicketPrice().compareTo(BigDecimal.valueOf(0)) > 0);

      }
    }
    return myself;
  }

  public CustomAirlineCompanyWithRouteAssert hasValidRouteLeg() {
    Set<FlyRoute> routeList = actual.getRouteList();
    if (!routeList.isEmpty()) {
      for (FlyRoute route : routeList) {
        FlyRouteLeg leg = route.getRouteLeg();
        assertThat(leg.getArrivalAirport()).isNotNull();
        assertThat(leg.getArrivalDateTime()).isNotNull();
        assertThat(leg.getDepartureAirport()).isNotNull();
        assertThat(leg.getDepartureDateTime()).isNotNull();
      }
    }
    return myself;
  }


}
