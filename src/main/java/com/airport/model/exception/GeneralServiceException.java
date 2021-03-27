package com.airport.model.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
@SuppressWarnings("serial")
public class GeneralServiceException extends RuntimeException {

  public GeneralServiceException(String message) {
    super(message);
  }

}
