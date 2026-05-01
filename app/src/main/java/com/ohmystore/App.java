package com.ohmystore;

import com.ohmystore.config.Database;
import com.ohmystore.dto.NewProductRequest;
import com.ohmystore.exception.ValidationException;
import com.ohmystore.model.Product;
import com.ohmystore.repository.ProductRepository;
import com.ohmystore.type.NonEmptyString;
import com.ohmystore.type.PriceCents;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class App {
  public static void main(String[] args) {

    Dotenv dotenv = Dotenv.load();

    String url = dotenv.get("DATABASE_URL");
    String user = dotenv.get("OMS_USER");
    String password = dotenv.get("PASSWORD");

    Database db = new Database(url, user, password);
    ProductRepository productRepo = new ProductRepository(db);

    try {
      NonEmptyString name = new NonEmptyString("SLR Camera");
      PriceCents priceCents = new PriceCents(-12000);
      NewProductRequest req = new NewProductRequest(name, priceCents);
      Product newProduct = productRepo.save(req);
      System.out.println(newProduct);
    } catch (SQLException e) {
      System.err.println(e);
    } catch (ValidationException e) {
      System.err.println(e);
    }
    try {
      List<Product> products = productRepo.findAll();
      for (Product p : products) {
        System.out.println(p);
      }
      System.out.println();
      List<Product> productsLimit = productRepo.findAllLimited(2);
      for (Product p : productsLimit) {
        System.out.println(p);
      }

      Optional<Product> productById = productRepo.findById(3);
      if (productById.isPresent()) {
        System.out.println(productById.get());
      } else {
        System.err.println("Product not found");
      }

      boolean deleted = productRepo.delete(productById.get().getId());
      if (deleted) {
        System.out.println("Product deleted");
      } else {
        System.err.println("Product not found");
      }
    } catch (SQLException e) {
      System.err.println("Error returned: " + e);
    }
  }
}
