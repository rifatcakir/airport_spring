package com.airport;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import com.airport.custom.CustomAirlineCompanyWithRouteAssert;
import com.airport.model.AirlineCompanyDTO;
import com.airport.model.AirlineCompanyWithRouteDTO;
import com.airport.persistence.entity.AirlineCompany;
import com.airport.persistence.entity.FlyRoute;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile({"unit-test", "integration-test"})
public class AirlineCompanyApplicationUnitTestSupport {

  @Autowired
  private MockMvc mockMvc;

  public void createAirlineCompany(AirlineCompanyDTO mockCreateRequest) throws Exception {
    // @formatter:off
    this.mockMvc.perform(post("/airline/company/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockCreateRequest)))
    .andDo(print())
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.uuid", notNullValue()))
    .andExpect(jsonPath("$.foundationDate", notNullValue()))
    .andExpect(jsonPath("$.companyName",is(mockCreateRequest.getCompanyName())));
     // @formatter:on
  }



  public void searcAirlineCompanyByName(AirlineCompany mockCompany) throws Exception {
    // @formatter:off
    this.mockMvc.perform(get("/airline/company/search/?companyName="+mockCompany.getCompanyName()).contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.uuid", notNullValue()))
      .andExpect(jsonPath("$.foundationDate", notNullValue()))
      .andExpect(jsonPath("$.countryName", is(mockCompany.getCountryName())))
      .andExpect(jsonPath("$.companyName",is(mockCompany.getCompanyName())));
    // @formatter:on
  }

  public void searcAirlineCompanyByNameWhenErrorThrown() throws Exception {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(get("/airline/company/search/?companyName=1111").contentType(MediaType.APPLICATION_JSON))
      .andDo(print());
    });

    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Airline company not found!";
    assertEquals(expectedError, actualError);
    // @formatter:on

  }

  public void assignRouteToAirline(AirlineCompany mockCompany, Optional<FlyRoute> mockFlyRoute, AirlineCompanyWithRouteDTO airlineCompanyWithRouteDTO)
      throws Exception {
    // @formatter:off
    this.mockMvc.perform(post("/airline/company/assign/route").contentType(MediaType.APPLICATION_JSON).content(asJsonString(airlineCompanyWithRouteDTO)))
    .andDo(print())
    .andExpect(jsonPath("$.uuid", is(mockCompany.getUuid())))
    .andExpect(jsonPath("$.foundationDate", notNullValue()))
    .andExpect(jsonPath("$.companyName",is(mockCompany.getCompanyName())))
    .andExpect(jsonPath("$.countryName", is(mockCompany.getCountryName())))
    .andExpect(jsonPath("$.routeList", notNullValue()))
    .andExpect(jsonPath("$.routeList.[*].uuid", notNullValue()))
    .andExpect(jsonPath("$.routeList.[*].routeName", notNullValue()))
    .andExpect(jsonPath("$.routeList.[*].seatStatus", notNullValue()))
    .andExpect(jsonPath("$.routeList[*].routeLeg", notNullValue()))
    .andExpect(jsonPath("$.routeList[*].routeLeg.departureAirport", notNullValue()))
    .andExpect(jsonPath("$.routeList[*].routeLeg.arrivalAirport", notNullValue()))
    .andExpect(jsonPath("$.routeList[*].routeLeg.departureDateTime", notNullValue()))
    .andExpect(jsonPath("$.routeList[*].routeLeg.arrivalDateTime", notNullValue()))
    .andExpect(jsonPath("$.routeList[*].ticketPrice", notNullValue()))
    .andExpect(status().isOk());
    // @formatter:on
  }


  public void testEveryPartThatAssignRouteToAirline(AirlineCompany mockCompany) {
    // @formatter:off
    assertThatPaymentPlanForServiceResponse(mockCompany)
      .hasValidCompanyInformations()
      .hasValidRouteLeg()
      .hasValidRouteList();
    // @formatter:on
  }


  private CustomAirlineCompanyWithRouteAssert assertThatPaymentPlanForServiceResponse(AirlineCompany airlineCompany) {
    return new CustomAirlineCompanyWithRouteAssert(airlineCompany, CustomAirlineCompanyWithRouteAssert.class);
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }



  public void assignAirlineCompanyToRouteThenThrowCompanyNotFound(AirlineCompanyWithRouteDTO mockData) {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(post("/airline/company/assign/route").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockData)))
      .andDo(print());
    });

    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Company not found!";
    assertEquals(expectedError, actualError);
    // @formatter:on
  }



  public void assignAirlineCompanyToRouteThenThrowRouteNotFound(AirlineCompanyWithRouteDTO mockData) {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(post("/airline/company/assign/route").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockData)))
      .andDo(print());
    });

    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Route not found!";
    assertEquals(expectedError, actualError);
    // @formatter:on

  }


}
