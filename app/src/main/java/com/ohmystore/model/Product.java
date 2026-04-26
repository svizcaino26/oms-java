package com.ohmystore.model;

import java.time.LocalDateTime;

public class Product {
  private int id;
  private String name;
  private String description;
  private int priceCents;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Product(
      int id,
      String name,
      String description,
      int priceCents,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.priceCents = priceCents;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "Product: " + name + "\nID: " + id + "\nPrice: " + priceCents + "\n";
  }

  public int getId() {
    return id;
  }
}
