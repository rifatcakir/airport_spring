package com.airport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;
import org.hamcrest.Matchers;
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
import com.airport.model.TicketBuyRequest;
import com.airport.persistence.entity.FlyRoute;
import com.airport.persistence.entity.Ticket;
import com.airport.persistence.repository.FlyRouteRepository;
import com.airport.persistence.repository.TicketRepository;
import com.airport.service.TicketService;
import com.airport.service.component.FlyPriceUtility;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
@DisplayName("Ticket system unit-test")
@DirtiesContext
class TicketApplicationUnitTest {

  @Autowired
  private MocDataUtil mockDataUtil;

  @Autowired
  private TicketApplicationUnitTestSupport applicationTestSupport;

  @Autowired
  private TicketService ticketService;

  @Autowired
  private FlyPriceUtility flyPriceUtil;

  FlyRouteRepository routeRepository;

  TicketRepository ticketRepository;

  @BeforeAll
  public void setup() {
    routeRepository = Mockito.mock(FlyRouteRepository.class);
    ticketRepository = Mockito.mock(TicketRepository.class);
  }


  @Test
  void test_Buying_AValidTicket() throws Exception {
    assertNotNull(applicationTestSupport);
    TicketBuyRequest mockRequest = mockDataUtil.createMockTicketBuyRequest();
    FlyRoute flyRoute = mockDataUtil.createMockFlyRoute();

    Optional<FlyRoute> route = Optional.ofNullable(flyRoute);

    when(routeRepository.findById(mockRequest.getFlyRouteId())).thenReturn(route);
    when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(mockDataUtil.createMockTicketEntity());
    ticketService.setRouteRepository(routeRepository);
    ticketService.setTicketRepository(ticketRepository);
    applicationTestSupport.buyAValidTicket(mockRequest, flyRoute);
  }

  @Test
  void when_TicketBought_CheckSeatIsClosed() {

    assertNotNull(applicationTestSupport);
    TicketBuyRequest mockRequest = mockDataUtil.createMockTicketBuyRequest();
    FlyRoute flyRoute = mockDataUtil.createMockFlyRoute();

    boolean initialSeatStatus = flyRoute.getSeatStatus().get(mockRequest.getSeatNumber()).booleanValue();

    Optional<FlyRoute> route = Optional.ofNullable(flyRoute);

    when(routeRepository.findById(mockRequest.getFlyRouteId())).thenReturn(route);
    ticketService.setRouteRepository(routeRepository);

    Ticket ticket = ticketService.buyTicket(mockRequest);

    boolean finalSeatStatus = flyRoute.getSeatStatus().get(mockRequest.getSeatNumber()).booleanValue();

    assertThat(ticket.getSeatNumber()).isEqualTo(mockRequest.getSeatNumber());
    assertThat(initialSeatStatus).isFalse();
    assertThat(finalSeatStatus).isTrue();


  }

  @Test
  void when_TicketCanceled_CheckSeatIsOpen() {

    assertNotNull(applicationTestSupport);
    TicketBuyRequest mockRequest = mockDataUtil.createMockTicketBuyRequest();
    FlyRoute flyRoute = mockDataUtil.createMockFlyRoute();

    Ticket entity = mockDataUtil.createMockTicketEntity();
    flyRoute.getSeatStatus().put(entity.getSeatNumber(), true);
    entity.setFlyRoute(flyRoute);

    boolean initialSeatStatus = flyRoute.getSeatStatus().get(mockRequest.getSeatNumber()).booleanValue();

    Optional<Ticket> entityOpt = Optional.ofNullable(entity);
    Optional<FlyRoute> route = Optional.ofNullable(flyRoute);

    when(routeRepository.findById(mockRequest.getFlyRouteId())).thenReturn(route);
    when(ticketRepository.findById(entity.getTicketId())).thenReturn(entityOpt);

    ticketService.setRouteRepository(routeRepository);
    ticketService.setTicketRepository(ticketRepository);


    Ticket ticketCanceled = ticketService.cancelTicketById(entity.getTicketId());

    boolean finalSeatStatus = flyRoute.getSeatStatus().get(ticketCanceled.getSeatNumber()).booleanValue();


    assertThat(ticketCanceled.getSeatNumber()).isEqualTo(mockRequest.getSeatNumber());
    assertThat(ticketCanceled.getFlyRoute()).isNull();
    assertThat(initialSeatStatus).isTrue();
    assertThat(finalSeatStatus).isFalse();
  }

  @Test
  void when_SeatCapacity_NotChangeTenPercent_FlyPriceNotChanges() throws Exception {
    assertNotNull(applicationTestSupport);
    TicketBuyRequest mockRequest = mockDataUtil.createMockTicketBuyRequest();
    FlyRoute flyRoute = mockDataUtil.createMockFlyRoute();

    BigDecimal initialTicketPrice = flyRoute.getTicketPrice();
    int initialActiveSeat = flyPriceUtil.findSeatNumberInUse(flyRoute);

    Optional<FlyRoute> route = Optional.ofNullable(flyRoute);

    when(routeRepository.findById(mockRequest.getFlyRouteId())).thenReturn(route);
    ticketService.setRouteRepository(routeRepository);

    Ticket ticket = ticketService.buyTicket(mockRequest);
    int finalActiveSeat = flyPriceUtil.findSeatNumberInUse(flyRoute);

    // Calculate seat ratio
    double seatRation = Double.valueOf(finalActiveSeat) / flyRoute.getSeatStatus().size();

    assertThat(ticket).isNotNull();
    assertThat(route).isNotNull();
    assertThat(initialActiveSeat).isNotEqualTo(finalActiveSeat);
    assertThat(initialActiveSeat + 1).isEqualTo(finalActiveSeat);
    assertThat(seatRation).isNotEqualTo(Double.valueOf(0.1));
    assertThat(initialTicketPrice, Matchers.comparesEqualTo(route.get().getTicketPrice()));


  }

  @Test
  void when_SeatCapacity_ChangeTenPercent_FlyPriceChanges() throws Exception {
    assertNotNull(applicationTestSupport);
    FlyRoute flyRoute = mockDataUtil.createMockFlyRoute();

    // Allocate nine seats
    for (int i = 1; i < 10; i++) {
      flyRoute.getSeatStatus().put(i, true);
    }

    BigDecimal initialTicketPrice = flyRoute.getTicketPrice();
    int initialActiveSeat = flyPriceUtil.findSeatNumberInUse(flyRoute);

    Optional<FlyRoute> route = Optional.ofNullable(flyRoute);

    TicketBuyRequest mockRequest = mockDataUtil.createMockTicketBuyRequest();
    // Try to take 10th seat
    mockRequest.setSeatNumber(10);

    when(routeRepository.findById(mockRequest.getFlyRouteId())).thenReturn(route);
    ticketService.setRouteRepository(routeRepository);

    Ticket ticket = ticketService.buyTicket(mockRequest);
    int finalActiveSeat = flyPriceUtil.findSeatNumberInUse(flyRoute);

    // Calculate seat ratio
    double seatRation = Double.valueOf(finalActiveSeat) / flyRoute.getSeatStatus().size();

    // Calculate expected new ticket price
    BigDecimal finalSeatPrice = initialTicketPrice.multiply(BigDecimal.valueOf(1.1));

    assertThat(ticket).isNotNull();
    assertThat(route).isNotNull();
    assertThat(initialActiveSeat).isNotEqualTo(finalActiveSeat);
    assertThat(initialActiveSeat + 1).isEqualTo(finalActiveSeat);
    assertThat(seatRation).isEqualTo(Double.valueOf(0.1));
    assertThat(initialTicketPrice).isNotEqualTo(route.get().getTicketPrice());
    assertThat(finalSeatPrice, Matchers.comparesEqualTo(route.get().getTicketPrice()));


  }

  @Test
  void test_BuyingTicket_ForAlreadyAllocatedSeat() throws Exception {
    assertNotNull(applicationTestSupport);
    TicketBuyRequest mockRequest = mockDataUtil.createMockTicketBuyRequest();
    FlyRoute flyRoute = mockDataUtil.createMockFlyRoute();
    // Allocate seat
    flyRoute.getSeatStatus().put(mockRequest.getSeatNumber(), true);
    Optional<FlyRoute> route = Optional.ofNullable(flyRoute);

    when(routeRepository.findById(mockRequest.getFlyRouteId())).thenReturn(route);
    ticketService.setRouteRepository(routeRepository);

    applicationTestSupport.tryBuyingAlreadyAllocatedSeat(mockRequest);
  }

  @Test
  void test_BuyingTicket_ForNotExistsRoute() throws Exception {
    assertNotNull(applicationTestSupport);
    TicketBuyRequest mockRequest = mockDataUtil.createMockTicketBuyRequest();
    Optional<FlyRoute> route = Optional.empty();
    when(routeRepository.findById(mockRequest.getFlyRouteId())).thenReturn(route);
    ticketService.setRouteRepository(routeRepository);
    applicationTestSupport.tryBuyingTicketForNotExistsRoute(mockRequest);
  }

  @Test
  void test_BuyingTicket_OutOfSeatRange() {
    assertNotNull(applicationTestSupport);
    TicketBuyRequest mockRequest = mockDataUtil.createMockTicketBuyRequest();
    mockRequest.setSeatNumber(101);
    FlyRoute flyRoute = mockDataUtil.createMockFlyRoute();
    Optional<FlyRoute> route = Optional.ofNullable(flyRoute);

    when(routeRepository.findById(mockRequest.getFlyRouteId())).thenReturn(route);
    ticketService.setRouteRepository(routeRepository);

    applicationTestSupport.tryBuyingTicketOutOfSeatRange(mockRequest);
  }

  @Test
  void test_SearchValidTicket() throws Exception {
    assertNotNull(applicationTestSupport);
    Ticket mockTicket = mockDataUtil.createMockTicketEntity();
    Optional<Ticket> ticket = Optional.ofNullable(mockTicket);

    when(ticketRepository.findById(mockTicket.getTicketId())).thenReturn(ticket);
    ticketService.setTicketRepository(ticketRepository);
    applicationTestSupport.searchValidTicket(mockTicket);

  }


  @Test
  void test_SearchNotValidTicket() {
    assertNotNull(applicationTestSupport);
    Optional<Ticket> ticket = Optional.empty();
    String testId = "TEST";
    when(ticketRepository.findById(testId)).thenReturn(ticket);
    ticketService.setTicketRepository(ticketRepository);
    applicationTestSupport.searchNotValidTicket(testId);
  }


  @Test
  void test_CancelNotValidTicket() {
    assertNotNull(applicationTestSupport);
    Optional<Ticket> ticket = Optional.empty();
    String testId = "TEST";
    when(ticketRepository.findById(testId)).thenReturn(ticket);
    ticketService.setTicketRepository(ticketRepository);
    applicationTestSupport.cancelNotValidTicket(testId);
  }

  @Test
  void test_CancelValidTicket() throws Exception {
    assertNotNull(applicationTestSupport);
    Ticket mockTicket = mockDataUtil.createMockTicketEntity();
    Optional<Ticket> ticket = Optional.ofNullable(mockTicket);

    when(ticketRepository.findById(mockTicket.getTicketId())).thenReturn(ticket);
    ticketService.setTicketRepository(ticketRepository);

    applicationTestSupport.cancelValidTicket(mockTicket);
  }
}
