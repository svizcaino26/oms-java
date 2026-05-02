package com.ohmystore.repository;

import com.ohmystore.config.Database;
import com.ohmystore.dto.NewProductRequest;
import com.ohmystore.dto.UpdateProductRequest;
import com.ohmystore.exception.ValidationException;
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

  public boolean update(int id, UpdateProductRequest req) throws SQLException {
    boolean insertFields = false;
    List<Object> parameterList = new ArrayList<>();
    StringBuilder sql = new StringBuilder("UPDATE products SET ");

    if (req.name() != null) {
      sql.append("name = ?");
      parameterList.add(req.name().get());
      insertFields = true;
    }

    if (req.priceCents() != null) {
      if (insertFields) {
        sql.append(", ");
      }
      sql.append("price_cents = ?");
      parameterList.add(req.priceCents().get());
      insertFields = true;
    }

    if (req.description() != null) {
      if (insertFields) {
        sql.append(", ");
      }
      sql.append("description = ?");
      parameterList.add(req.description());
      insertFields = true;
    }

    if (!parameterList.isEmpty()) {
      sql.append(" WHERE id = ?");
      parameterList.add(id);
    } else {
      throw new ValidationException("Empty query when received while trying to update product");
    }

    String queryString = new String(sql);

    try (Connection conn = db.getConnection();
        PreparedStatement st = conn.prepareStatement(queryString)) {
      int i = 0;
      for (Object object : parameterList) {
        i += 1;
        if (object instanceof String) {
          st.setString(i, (String) object);
        } else {
          st.setInt(i, (int) object);
        }
      }
      int affectedRows = st.executeUpdate();
      return affectedRows > 0;
    }
  }
}
