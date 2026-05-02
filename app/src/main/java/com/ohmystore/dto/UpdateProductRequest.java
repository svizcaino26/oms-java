package com.ohmystore.dto;

import com.ohmystore.type.NonEmptyString;
import com.ohmystore.type.PriceCents;

public class UpdateProductRequest {
  private NonEmptyString name;
  private PriceCents priceCents;
  private String description;

  public UpdateProductRequest(Builder builder) {
    this.name = builder.name;
    this.priceCents = builder.priceCents;
    this.description = builder.description;
  }

  public NonEmptyString name() {
    return name;
  }

  public PriceCents priceCents() {
    return priceCents;
  }

  public String description() {
    return description;
  }

  public static class Builder {
    private NonEmptyString name;
    private PriceCents priceCents;
    private String description;

    public Builder name(NonEmptyString name) {
      this.name = name;
      return this;
    }

    public Builder priceCents(PriceCents priceCents) {
      this.priceCents = priceCents;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public UpdateProductRequest build() {
      return new UpdateProductRequest(this);
    }
  }
}
