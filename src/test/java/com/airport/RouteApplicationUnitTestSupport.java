package com.airport;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import com.airport.model.FlyRouteDTO;
import com.airport.persistence.entity.FlyRoute;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile({"unit-test", "integration-test"})
public class RouteApplicationUnitTestSupport {

  @Autowired
  private MockMvc mockMvc;

  public void createFlyRouteWithNotValidLeg(FlyRouteDTO mockFlyRoute) throws Exception {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(post("/airline/route/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockFlyRoute)))
      .andDo(print());
    });
    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Not a valid route leg!";
    assertEquals(expectedError, actualError);
  
     // @formatter:on
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void createFlyRoute(FlyRouteDTO createMockFlyRouteDTO) throws Exception {
    // @formatter:off
    this.mockMvc.perform(post("/airline/route/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(createMockFlyRouteDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.seatStatus",notNullValue()))
        .andExpect(jsonPath("$.routeLeg",notNullValue()))
        .andExpect(jsonPath("$.routeLeg.arrivalDateTime",notNullValue()))
        .andExpect(jsonPath("$.routeLeg.departureDateTime",notNullValue()))
        .andExpect(jsonPath("$.routeLeg.departureAirport",is(createMockFlyRouteDTO.getRouteLeg().getDepartureAirport())))
        .andExpect(jsonPath("$.routeLeg.arrivalAirport",is(createMockFlyRouteDTO.getRouteLeg().getArrivalAirport())))
        .andExpect(jsonPath("$.routeName", is(createMockFlyRouteDTO.getRouteName())));
     // @formatter:on
  }

  public void searchAirportRouteById(String uuid) throws Exception {
    // @formatter:off
    this.mockMvc.perform(get("/airline/route/search/Id?routeId="+uuid).contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.uuid", notNullValue()));
    // @formatter:on
  }

  public void searchAirportRouteById_NotFound(String uuid) {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(get("/airline/route/search/Id?routeId="+uuid).contentType(MediaType.APPLICATION_JSON))
      .andDo(print());
    });

    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Route not found!";
    assertEquals(expectedError, actualError);
    // @formatter:on
  }

  public void searchAirportRouteByName(FlyRoute mockEntity) throws Exception {
    // @formatter:off
    this.mockMvc.perform(get("/airline/route/search/routeName?routeName=TEST").contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.[*].seatStatus",notNullValue()))
      .andExpect(jsonPath("$.[*].routeLeg",notNullValue()))
      ;
    // @formatter:on
  }

}
