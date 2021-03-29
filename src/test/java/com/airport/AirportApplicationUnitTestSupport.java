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
import com.airport.model.AirportDTO;
import com.airport.persistence.entity.Airport;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile({"unit-test", "integration-test"})
public class AirportApplicationUnitTestSupport {

  @Autowired
  private MockMvc mockMvc;

  public void createAirport(AirportDTO createMockAirportDTO) throws Exception {
    // @formatter:off
    this.mockMvc.perform(post("/airport/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(createMockAirportDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(createMockAirportDTO.getName())))
        .andExpect(jsonPath("$.uuid", notNullValue()))
        .andExpect(jsonPath("$.foundationDate", notNullValue()))
        .andExpect(jsonPath("$.cityName", is(createMockAirportDTO.getCityName())))
        .andExpect(jsonPath("$.countryName",is(createMockAirportDTO.getCountryName())));
     // @formatter:on
  }

  public void searchAirport(String name, Airport entity) throws Exception {
    // @formatter:off
    this.mockMvc.perform(get("/airport/search?name="+name).contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.uuid", notNullValue()))
      .andExpect(jsonPath("$.foundationDate", notNullValue()))
      .andExpect(jsonPath("$.countryName", is(entity.getCountryName())))
      .andExpect(jsonPath("$.name", is(entity.getName())))
      .andExpect(jsonPath("$.cityName",is(entity.getCityName())));
    // @formatter:on
  }

  public void searchNotExistsAirport() {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(get("/airport/search?name=1111").contentType(MediaType.APPLICATION_JSON))
      .andDo(print());
    });

    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Airport not found!";
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
}
