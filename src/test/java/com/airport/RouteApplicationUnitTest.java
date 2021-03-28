package com.airport;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import com.airport.model.FlyRouteDTO;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.repository.AirportRepository;
import com.airport.persistence.repository.FlyRouteRepository;
import com.airport.service.RouteService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
@DisplayName("Airline Route unit-test")
@DirtiesContext
class RouteApplicationUnitTest {

  @Autowired
  private MocDataUtil mockDataUtil;

  @Autowired
  private RouteApplicationUnitTestSupport applicationTestSupport;

  @Autowired
  private RouteService routeService;

  private FlyRouteRepository routeRepository;

  private AirportRepository airportRepository;

  @BeforeAll
  public void setup() {
    assertNotNull(mockDataUtil);
    airportRepository = Mockito.mock(AirportRepository.class);
    routeRepository = Mockito.mock(FlyRouteRepository.class);
  }

  @Test
  void testCreatingRoute() throws Exception {
    assertNotNull(applicationTestSupport);
    FlyRouteDTO mockDTO = mockDataUtil.createMockFlyRouteDTO();
    String arrivalPort = mockDTO.getRouteLeg().getArrivalAirport();
    String departurePort = mockDTO.getRouteLeg().getDepartureAirport();

    when(airportRepository.findByName(arrivalPort)).thenReturn(mockDataUtil.createMockAirport(arrivalPort));
    when(airportRepository.findByName(departurePort)).thenReturn(mockDataUtil.createMockAirport(departurePort));

    routeService.setAirportRepository(airportRepository);
    applicationTestSupport.createFlyRoute(mockDataUtil.createMockFlyRouteDTO());
  }

  @Test
  void testCreatingRouteWithNotValidLeg() throws Exception {
    assertNotNull(applicationTestSupport);
    when(airportRepository.findByName(anyString())).thenReturn(null);
    routeService.setAirportRepository(airportRepository);
    applicationTestSupport.createFlyRouteWithNotValidLeg(mockDataUtil.createMockFlyRouteDTO());
  }

  @Test
  void searchRouteById() throws Exception {
    assertNotNull(applicationTestSupport);
    Optional<FlyRoute> mockData = Optional.of(mockDataUtil.createMockFlyRoute());
    when(routeRepository.findById(mockData.get().getUuid())).thenReturn(mockData);
    routeService.setRouteRepository(routeRepository);
    applicationTestSupport.searchAirportRouteById(mockData.get().getUuid());
  }

  @Test
  void searchRouteById_NotFound() throws Exception {
    assertNotNull(applicationTestSupport);
    String uuid = "1111";
    applicationTestSupport.searchAirportRouteById_NotFound(uuid);
  }

  @Test
  void searchRouteByName() throws Exception {
    assertNotNull(applicationTestSupport);
    Set<FlyRoute> mockData = new HashSet<>();
    FlyRoute mockEntity = mockDataUtil.createMockFlyRoute();
    mockData.add(mockEntity);
    when(routeRepository.findByRouteName("TEST")).thenReturn(mockData);
    routeService.setRouteRepository(routeRepository);
    applicationTestSupport.searchAirportRouteByName(mockEntity);
  }

}
