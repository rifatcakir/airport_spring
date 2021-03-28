package com.airport.service.component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;
import com.airport.persistence.entity.FlyRoute;

@Component
public class CalculateFlyPrice {

  public void recalculateFlyPrice(FlyRoute flyRoute) {
    Map<Integer, Boolean> flyStatus = flyRoute.getSeatStatus();
    int seatNumberInUse = 0;
    for (Entry<Integer, Boolean> entry : flyStatus.entrySet()) {
      if (entry.getValue().booleanValue()) {
        seatNumberInUse++;
      }
    }
    if (seatNumberInUse > 1) {
      int mod = seatNumberInUse % 10;
      int incLimit = mod * 10 + 1;
      if (incLimit == seatNumberInUse) {
        increasePrice(flyRoute);
      } else {
        int decLimit = (mod + 1) * 10 - 1;
        if (decLimit == seatNumberInUse) {
          decreasePrice(flyRoute);
        }
      }
    }
  }

  private void decreasePrice(FlyRoute flyRoute) {
    BigDecimal currentPrice = flyRoute.getTicketPrice();
    BigDecimal newPrice = currentPrice.multiply(BigDecimal.valueOf(0.9));
    flyRoute.setTicketPrice(newPrice);
  }

  private void increasePrice(FlyRoute flyRoute) {
    BigDecimal currentPrice = flyRoute.getTicketPrice();
    BigDecimal newPrice = currentPrice.multiply(BigDecimal.valueOf(110)).divide(BigDecimal.valueOf(110));
    flyRoute.setTicketPrice(newPrice);
  }

}
