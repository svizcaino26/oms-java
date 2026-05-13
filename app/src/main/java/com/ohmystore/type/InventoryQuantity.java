package com.ohmystore.type;

import com.ohmystore.exception.ValidationException;

public class InventoryQuantity {
  private final int value;

  public InventoryQuantity(int value) throws ValidationException {
    if (value < 0) {
      throw new ValidationException("Inventory quantity cannot be negative");
    }
    this.value = value;
  }

  public int get() {
    return value;
  }
}
