package com.airport.service;

import org.springframework.stereotype.Component;

@Component
public class CreditCardUtility {

  public String maskCCN(String creditCardNo) {
    String cardNo = cleanCardNo(creditCardNo);
    return maskCardNo(cardNo);
  }

  private String maskCardNo(String cardNo) {
    String start = cardNo.substring(0, 6);
    String end = cardNo.substring(11, 15);
    return start + "******" + end;
  }

  private String cleanCardNo(String creditCardNo) {
    return creditCardNo.replaceAll("[^\\d.]", "");
  }

}
