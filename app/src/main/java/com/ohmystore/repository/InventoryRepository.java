package com.ohmystore.repository;

import com.ohmystore.config.Database;
import com.ohmystore.exception.NotFoundException;
import com.ohmystore.type.InventoryQuantity;
import java.sql.*;

public class InventoryRepository {
  private final Database db;
  private final ProductRepository productRepo;

  public InventoryRepository(Database db) {
    this.db = db;
    this.productRepo = new ProductRepository(db);
  }

  public boolean increaseStock(int id, InventoryQuantity quantity)
      throws SQLException, NotFoundException {
    productRepo.findById(id);

    try (Connection conn = db.getConnection();
        PreparedStatement st =
            conn.prepareStatement(
                """
                  INSERT INTO inventory (product_id, quantity)
                  VALUES (?, ?)
                  ON CONFLICT (product_id) DO UPDATE SET quantity = inventory.quantity + ?
                """)) {
      st.setInt(1, id);
      st.setInt(2, quantity.get());
      st.setInt(3, quantity.get());

      int affectedRows = st.executeUpdate();

      return affectedRows > 0;
    }
  }
}
