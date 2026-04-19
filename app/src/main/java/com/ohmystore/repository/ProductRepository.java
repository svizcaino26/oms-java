package com.ohmystore.repository;

import java.sql.*;
import java.time.LocalDateTime;

import com.ohmystore.config.Database;
import com.ohmystore.model.Product;
import com.ohmystore.dto.NewProductRequest;

public class ProductRepository {
    private final Database db;

    public ProductRepository (Database db) {
        this.db = db;
    }

    public Product save (NewProductRequest req) throws SQLException{
        try (
            Connection conn = db.getConnection();
            PreparedStatement st = conn.prepareStatement("""
                    INSERT INTO products (name, price_cents, description)
                    VALUES (?, ?, ?)
                    RETURNING *
            """)
        ) {
            st.setString(1, req.name());
            st.setInt(2, req.priceCents());
            st.setString(3, req.description());

            try (ResultSet rs = st.executeQuery()) {
                rs.next();

                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("price_cents"),
                    rs.getObject("created_at", LocalDateTime.class),
                    rs.getObject("updated_at", LocalDateTime.class)
                );
            }
        }
    }
}
