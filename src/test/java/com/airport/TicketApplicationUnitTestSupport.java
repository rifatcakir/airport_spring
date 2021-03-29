package com.airport;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;
import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.entity.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile({"unit-test", "integration-test"})
public class TicketApplicationUnitTestSupport {

  @Autowired
  private MockMvc mockMvc;

  public void buyAValidTicket(TicketBuyRequest mockRequest, FlyRoute flyRoute) throws Exception {
    // @formatter:off
    ResultActions result = this.mockMvc.perform(post("/ticket/buy").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest)));
        
    result
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.ticketId", notNullValue()))
        .andExpect(jsonPath("$.flyRoute", notNullValue()))
        .andExpect(jsonPath("$.flyRoute.flyRouteId", notNullValue()))
        .andExpect(jsonPath("$.flyRoute.ticketPrice").doesNotExist())
        .andExpect(jsonPath("$.flyRoute.routeName", is(flyRoute.getRouteName())))
        .andExpect(jsonPath("$.flyRoute.routeLeg", notNullValue()))
        .andExpect(jsonPath("$.flyRoute.routeLeg.departureDateTime", notNullValue()))
        .andExpect(jsonPath("$.flyRoute.routeLeg.arrivalDateTime", notNullValue()))
        .andExpect(jsonPath("$.flyRoute.routeLeg.departureAirport", is(flyRoute.getRouteLeg().getDepartureAirport())))
        .andExpect(jsonPath("$.flyRoute.routeLeg.arrivalAirport", is(flyRoute.getRouteLeg().getArrivalAirport())))
        .andExpect(jsonPath("$.seatNumber", is(mockRequest.getSeatNumber())))
        .andExpect(jsonPath("$.creditCardNumber").value(not(mockRequest.getCreditCardNumber())))
        .andExpect(jsonPath("$.ticketPrice").value(mockRequest.getTicketPrice()))
        ;
     // @formatter:on
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void tryBuyingAlreadyAllocatedSeat(TicketBuyRequest mockRequest) throws Exception {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(post("/ticket/buy").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest)));  
    });
    
    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Seat is not empty!";
    assertEquals(expectedError, actualError);
     // @formatter:on
  }

  public void tryBuyingTicketForNotExistsRoute(TicketBuyRequest mockRequest) {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(post("/ticket/buy").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest)));  
    });
    
    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Route not found!";
    assertEquals(expectedError, actualError);
     // @formatter:on
  }

  public void tryBuyingTicketOutOfSeatRange(TicketBuyRequest mockRequest) {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(post("/ticket/buy").contentType(MediaType.APPLICATION_JSON).content(asJsonString(mockRequest)));  
    });
    
    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Ticket buy denied! Ticket number out of range!";
    assertEquals(expectedError, actualError);
     // @formatter:on
  }

  public void searchValidTicket(Ticket mockTicket) throws Exception {
    FlyRoute flyRoute = mockTicket.getFlyRoute();
    // @formatter:off
    this.mockMvc.perform(get("/ticket/search/Id?ticketId="+mockTicket.getTicketId()).contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.ticketId", notNullValue()))
      .andExpect(jsonPath("$.flyRoute", notNullValue()))
      .andExpect(jsonPath("$.flyRoute.flyRouteId", notNullValue()))
      .andExpect(jsonPath("$.flyRoute.routeName", is(flyRoute.getRouteName())))
      .andExpect(jsonPath("$.flyRoute.routeLeg", notNullValue()))
      .andExpect(jsonPath("$.flyRoute.ticketPrice").doesNotExist())
      .andExpect(jsonPath("$.flyRoute.routeLeg.departureDateTime", notNullValue()))
      .andExpect(jsonPath("$.flyRoute.routeLeg.arrivalDateTime", notNullValue()))
      .andExpect(jsonPath("$.flyRoute.routeLeg.departureAirport", is(flyRoute.getRouteLeg().getDepartureAirport())))
      .andExpect(jsonPath("$.flyRoute.routeLeg.arrivalAirport", is(flyRoute.getRouteLeg().getArrivalAirport())))
      .andExpect(jsonPath("$.seatNumber", is(mockTicket.getSeatNumber())))
      .andExpect(jsonPath("$.ticketPrice").value(mockTicket.getTicketPrice()))
      ;
    
    // @formatter:on
  }

  public void searchNotValidTicket(String testId) {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(get("/ticket/search/Id?ticketId="+testId).contentType(MediaType.APPLICATION_JSON));
    });
    
    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Ticket not found!";
    assertEquals(expectedError, actualError);
    // @formatter:on
  }

  public void cancelNotValidTicket(String testId) {
    // @formatter:off
    NestedServletException exception = assertThrows(NestedServletException.class, () ->{
      this.mockMvc.perform(get("/ticket/cancel/Id?ticketId="+testId).contentType(MediaType.APPLICATION_JSON));
    });
    
    String actualError = exception.getCause().getLocalizedMessage();
    String expectedError = "Ticket not found!";
    assertEquals(expectedError, actualError);
    // @formatter:on
  }

  public void cancelValidTicket(Ticket mockTicket) throws Exception {
    // @formatter:off
    ResultActions result = this.mockMvc.perform(get("/ticket/cancel/Id?ticketId="+mockTicket.getTicketId()).contentType(MediaType.APPLICATION_JSON));
        
    result
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.ticketId", notNullValue()))
        .andExpect(jsonPath("$.flyRoute", nullValue()))
        .andExpect(jsonPath("$.seatNumber", is(mockTicket.getSeatNumber())))
        .andExpect(jsonPath("$.creditCardNumber").value(mockTicket.getCreditCardNumber()))
        .andExpect(jsonPath("$.ticketPrice").value(mockTicket.getTicketPrice()))
        ;
     // @formatter:on
  }

}
