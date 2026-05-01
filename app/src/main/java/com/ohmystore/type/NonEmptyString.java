package com.ohmystore.type;

import com.ohmystore.exception.ValidationException;

public final class NonEmptyString {
  private final String value;

  public NonEmptyString(String value) throws ValidationException {
    if (value == null || value.isEmpty()) {
      throw new ValidationException("Name is required");
    }
    this.value = value;
  }

  public String get() {
    return value;
  }
}
