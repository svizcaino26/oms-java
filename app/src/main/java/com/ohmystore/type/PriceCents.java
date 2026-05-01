package com.ohmystore.type;

import com.ohmystore.exception.ValidationException;

public final class PriceCents {
  private final int value;

  public PriceCents(int value) throws ValidationException {
    if (value < 0) {
      throw new ValidationException("Price cannot be negative");
    }
    this.value = value;
  }

  public int get() {
    return value;
  }
}
