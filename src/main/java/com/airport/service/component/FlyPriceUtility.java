package com.airport.service.component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;
import com.airport.persistence.entity.FlyRoute;

@Component
public class FlyPriceUtility {

  public void checkForIncreaseTicketPrice(FlyRoute flyRoute) {
    priceCalculator(flyRoute, true);
  }

  private void priceCalculator(FlyRoute flyRoute, boolean isIncrease) {
    int seatNumberInUse = findSeatNumberInUse(flyRoute);

    int mod = seatNumberInUse % 10;
    if (isIncrease && mod == 0) {
      increasePrice(flyRoute);
    }
  }

  private int findSeatNumberInUse(FlyRoute flyRoute) {
    int seatNumberInUse = 0;
    Map<Integer, Boolean> flyStatus = flyRoute.getSeatStatus();
    for (Entry<Integer, Boolean> entry : flyStatus.entrySet()) {
      if (entry.getValue().booleanValue()) {
        seatNumberInUse++;
      }
    }
    return seatNumberInUse;
  }

  private void increasePrice(FlyRoute flyRoute) {
    BigDecimal currentPrice = flyRoute.getTicketPrice();
    BigDecimal newPrice = currentPrice.multiply(BigDecimal.valueOf(1.1));
    flyRoute.setTicketPrice(newPrice);
  }

}
