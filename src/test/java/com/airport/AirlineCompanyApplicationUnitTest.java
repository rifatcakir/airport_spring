package com.airport;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.Optional;
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
import com.airport.model.AirlineCompanyDTO;
import com.airport.model.AirlineCompanyWithRouteDTO;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.repository.AirlineCompanyRepository;
import com.airport.persistence.repository.FlyRouteRepository;
import com.airport.service.AirlineCompanyService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
@DisplayName("Airline Company unit-test")
@DirtiesContext
class AirlineCompanyApplicationUnitTest {

  @Autowired
  AirlineCompanyApplicationUnitTestSupport applicationTestSupport;

  @Autowired
  AirlineCompanyService airlineCompanyService;


  @Autowired
  private MocDataUtil mockDataUtil;

  private AirlineCompanyRepository airlineRepository;

  private FlyRouteRepository routeRepository;

  private AirlineCompanyDTO mockCreateRequest;

  private AirlineCompany mockCompany;

  @BeforeAll
  public void setup() {
    airlineRepository = Mockito.mock(AirlineCompanyRepository.class);
    routeRepository = Mockito.mock(FlyRouteRepository.class);
    mockCreateRequest = mockDataUtil.createMockAirlineCompanyRequest();
    mockCompany = mockDataUtil.createMockAirlineCompanyEntity();
  }

  @Test
  void testCreatingAirlineCompany() throws Exception {
    assertNotNull(applicationTestSupport);
    applicationTestSupport.createAirlineCompany(mockCreateRequest);
  }

  @Test
  void testSearchingAirlineCompanyByName() throws Exception {
    assertNotNull(applicationTestSupport);
    assertNotNull(mockDataUtil);

    when(airlineRepository.findByCompanyName(mockCompany.getCompanyName())).thenReturn(mockCompany);

    airlineCompanyService.setAirlineRepository(airlineRepository);

    applicationTestSupport.searcAirlineCompanyByName(mockCompany);
  }

  @Test
  void whenCompanyNotExists_SearchingAirlineCompany_ReturnError() throws Exception {
    assertNotNull(applicationTestSupport);
    assertNotNull(mockDataUtil);
    applicationTestSupport.searcAirlineCompanyByNameWhenErrorThrown();
  }
  
  @Test
  void whenCompanyNotExists_AssignToAirlineCompany_ReturnError() throws Exception {
    assertNotNull(applicationTestSupport);
    assertNotNull(mockDataUtil);
    AirlineCompanyWithRouteDTO mockData = mockDataUtil.createAirlineCompanyWithRouteDTO();
    applicationTestSupport.assignAirlineCompanyToRouteThenThrowCompanyNotFound(mockData);
  }
  
  @Test
  void whenRouteNotExists_AssignToAirlineCompany_ReturnError() throws Exception {
    assertNotNull(applicationTestSupport);
    assertNotNull(mockDataUtil);
    AirlineCompanyWithRouteDTO mockData = mockDataUtil.createAirlineCompanyWithRouteDTO();
    Optional<AirlineCompany> company = Optional.ofNullable(mockCompany);
    when(airlineRepository.findById("1")).thenReturn(company);

    airlineCompanyService.setAirlineRepository(airlineRepository);
    applicationTestSupport.assignAirlineCompanyToRouteThenThrowRouteNotFound(mockData);
  }

  @Test
  void testAssignRouteToAirlineCompany() throws Exception {
    assertNotNull(applicationTestSupport);
    assertNotNull(mockDataUtil);

    Optional<AirlineCompany> company = Optional.ofNullable(mockCompany);
    when(airlineRepository.findById("1")).thenReturn(company);

    airlineCompanyService.setAirlineRepository(airlineRepository);
    Optional<FlyRoute> mockFlyRoute = Optional.ofNullable(mockDataUtil.createMockFlyRoute());
    when(routeRepository.findById("1")).thenReturn(mockFlyRoute);

    airlineCompanyService.setRouteRepository(routeRepository);

    applicationTestSupport.assignRouteToAirline(mockCompany, mockFlyRoute, mockDataUtil.createAirlineCompanyWithRouteDTO());
  }
  
  @Test
  void test_EveryPart_ThatAsignRouteToAirlineCompany() {
    assertNotNull(applicationTestSupport);
    applicationTestSupport.testEveryPartThatAssignRouteToAirline(mockCompany);
  }

}
