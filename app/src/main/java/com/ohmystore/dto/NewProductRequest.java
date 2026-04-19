package com.ohmystore.dto;

public class NewProductRequest {
  private String name;
  private int priceCents;
  private String description;

  public NewProductRequest(String name, int priceCents) {
    this.name = name;
    this.priceCents = priceCents;
  }

  public NewProductRequest(String name, int priceCents, String description) {
    this.name = name;
    this.priceCents = priceCents;
    this.description = description;
  }

  public void validate() throws IllegalArgumentException {
    if (priceCents < 0) {
      throw new IllegalArgumentException("Price must be >= 0");
    }
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Name is required");
    }
  }

  public String name () {
    return name;
  }

  public int priceCents () {
    return priceCents;
  }

  public String description () {
    return description;
  }
}
