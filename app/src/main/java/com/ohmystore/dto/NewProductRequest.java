package com.ohmystore.dto;

class NewProductRequest {
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
  }
}
