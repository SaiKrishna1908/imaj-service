package com.imager.core.exceptions;

public class ResizeException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;
  
  public ResizeException(String s) {
    super(s);
  }
  
  @Override
  public String getMessage() {
    return "Resizing Failed";
  }
}
