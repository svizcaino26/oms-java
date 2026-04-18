package com.ohmystore;

import com.ohmystore.model.Product;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.time.LocalDateTime;

public class App {
  public static void main(String[] args) {

    Dotenv dotenv = Dotenv.load();

    String dbURL = dotenv.get("DATABASE_URL");
    String user = dotenv.get("USER");
    String password = dotenv.get("PASSWORD");
    try {
      Connection db = DriverManager.getConnection(dbURL, user, password);
    } catch (SQLDataException e) {
      System.err.println(e);
    }

    Product newProduct =
        new Product(1, "laptop", "just a laptop", 10000, LocalDateTime.now(), LocalDateTime.now());

    System.out.println(newProduct);
    System.out.println(db);
  }
}
