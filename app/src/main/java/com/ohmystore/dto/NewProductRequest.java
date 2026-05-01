package com.ohmystore.dto;

import com.ohmystore.type.NonEmptyString;
import com.ohmystore.type.PriceCents;

public class NewProductRequest {
  private NonEmptyString name;
  private PriceCents priceCents;
  private String description;

  public NewProductRequest(NonEmptyString name, PriceCents priceCents) {
    this.name = name;
    this.priceCents = priceCents;
  }

  public NewProductRequest(NonEmptyString name, PriceCents priceCents, String description) {
    this.name = name;
    this.priceCents = priceCents;
    this.description = description;
  }

  public String name() {
    return name.get();
  }

  public int priceCents() {
    return priceCents.get();
  }

  public String description() {
    return description;
  }
}
