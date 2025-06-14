package com.doubledeltas.minecollector.lang;

public class InvalidLangException extends RuntimeException {
  public InvalidLangException() {
    super();
  }

  public InvalidLangException(String msg) {
    super(msg);
  }

  public InvalidLangException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
