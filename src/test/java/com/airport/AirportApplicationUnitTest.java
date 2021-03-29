package com.airport;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.airport.persistence.entity.Airport;
import com.airport.persistence.repository.AirportRepository;
import com.airport.service.AirportService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
@DisplayName("Airport unit-test")
@DirtiesContext
class AirportApplicationUnitTest {

  @Autowired
  private MocDataUtil mockDataUtil;

  @Autowired
  private AirportService airportService;

  @Autowired
  private AirportApplicationUnitTestSupport applicationTestSupport;

  AirportRepository airportRepository;

  @BeforeAll
  public void setup() {
    airportRepository = Mockito.mock(AirportRepository.class);
  }

  @Test
  void testCreatingAirport() throws Exception {
    assertNotNull(applicationTestSupport);
    applicationTestSupport.createAirport(mockDataUtil.createMockAirportDTO());
  }

  @Test
  void searchAirportByName() throws Exception {
    assertNotNull(applicationTestSupport);
    Airport entity = mockDataUtil.createAirportEntity();
    when(airportRepository.findByName(anyString())).thenReturn(entity);

    airportService.setAirportRepository(airportRepository);
    applicationTestSupport.searchAirport(entity.getName(), entity);
  }

  @Test
  void searchNotExistsAirport_ThenTrowError() {
    assertNotNull(applicationTestSupport);
    applicationTestSupport.searchNotExistsAirport();
  }

}
