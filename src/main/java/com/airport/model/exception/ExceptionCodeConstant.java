package com.airport.model.exception;

/*
 * Constants of general service based special exceptions.
 * 
 */
public final class ExceptionCodeConstant {

  // 4XXX Exceptions //
  public static final int NOT_ACCEPTABLE = 406;

  // PRIVATE //

  /**
   * The caller references the constants using <tt>ExceptionCodeConstant.EMPTY_STRING</tt>, and so on.
   * Thus, the caller should be prevented from constructing objects of this class, by declaring this
   * private constructor.
   */
  private ExceptionCodeConstant() {
    // this prevents even the native class from
    // calling this ctor as well :
    throw new AssertionError();
  }

}
