package com.ohmystore.repository;

import com.ohmystore.config.Database;
import com.ohmystore.dto.NewProductRequest;
import com.ohmystore.model.Product;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
  private final Database db;

  public ProductRepository(Database db) {
    this.db = db;
  }

  private Product mapRow(ResultSet rs) throws SQLException {
    return new Product(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getString("description"),
        rs.getInt("price_cents"),
        rs.getObject("created_at", LocalDateTime.class),
        rs.getObject("updated_at", LocalDateTime.class));
  }

  public Product save(NewProductRequest req) throws SQLException {
    try (Connection conn = db.getConnection();
        PreparedStatement st =
            conn.prepareStatement(
                """
                        INSERT INTO products (name, price_cents, description)
                        VALUES (?, ?, ?)
                        RETURNING *
                """)) {
      st.setString(1, req.name());
      st.setInt(2, req.priceCents());
      st.setString(3, req.description());

      try (ResultSet rs = st.executeQuery()) {
        rs.next();
        return mapRow(rs);
      }
    }
  }

  public List<Product> findAll() throws SQLException {
    try (Connection conn = db.getConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM products")) {
      List<Product> products = new ArrayList<>();
      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          products.add(mapRow(rs));
        }
      }
      return products;
    }
  }

  public List<Product> findAllLimited(int limit) throws SQLException {
    try (Connection conn = db.getConnection();
        PreparedStatement st =
            conn.prepareStatement(
                """
                  SELECT * FROM products
                  LIMIT ?
                """)) {
      st.setInt(1, limit);
      List<Product> products = new ArrayList<>(limit);
      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          products.add(mapRow(rs));
        }
      }
      return products;
    }
  }

  public Optional<Product> findById(int id) throws SQLException {
    try (Connection conn = db.getConnection();
        PreparedStatement st =
            conn.prepareStatement(
                """
                  SELECT * FROM products
                  WHERE id = ?
                """)) {
      st.setInt(1, id);
      try (ResultSet rs = st.executeQuery()) {
        if (!rs.next()) {
          return Optional.empty();
        }
        return Optional.of(mapRow(rs));
      }
    }
  }

  public boolean delete(int id) throws SQLException {
    try (Connection conn = db.getConnection();
        PreparedStatement st =
            conn.prepareStatement(
                """
                  DELETE FROM products
                  WHERE id = ?
                """)) {
      st.setInt(1, id);

      int affectedRows = st.executeUpdate();

      return affectedRows > 0;
    }
  }
}
