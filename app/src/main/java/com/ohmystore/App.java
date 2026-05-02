package com.ohmystore;

import com.ohmystore.config.Database;
import com.ohmystore.dto.UpdateProductRequest;
import com.ohmystore.exception.ValidationException;
import com.ohmystore.repository.ProductRepository;
import com.ohmystore.type.PriceCents;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.SQLException;

public class App {
  public static void main(String[] args) {

    Dotenv dotenv = Dotenv.load();

    String url = dotenv.get("DATABASE_URL");
    String user = dotenv.get("OMS_USER");
    String password = dotenv.get("PASSWORD");

    Database db = new Database(url, user, password);
    ProductRepository productRepo = new ProductRepository(db);
    // create new product example
    try {
      // NewProductRequest req =
      //     new NewProductRequest(new NonEmptyString("SLR Camera"), new PriceCents(12000));
      // Product newProduct = productRepo.save(req);
      // System.out.println(newProduct);

      int id = 5;
      UpdateProductRequest updateReq =
          new UpdateProductRequest.Builder()
              .priceCents(new PriceCents(125000))
              .description("Sony SLR camera")
              .build();

      boolean updated = productRepo.update(id, updateReq);
      if (updated) {
        System.out.println("product updated");
      }
    } catch (SQLException e) {
      System.err.println(e);
    } catch (ValidationException e) {
      System.err.println(e);
    }

    // list all products
    // try {
    //   List<Product> products = productRepo.findAll();
    //   for (Product p : products) {
    //     System.out.println(p);
    //   }
    //   System.out.println();
    //   List<Product> productsLimit = productRepo.findAllLimited(2);
    //   for (Product p : productsLimit) {
    //     System.out.println(p);
    //   }

    //   Optional<Product> productById = productRepo.findById(3);
    //   if (productById.isPresent()) {
    //     System.out.println(productById.get());
    //   } else {
    //     System.err.println("Product not found");
    //   }

    //   boolean deleted = productRepo.delete(productById.get().getId());
    //   if (deleted) {
    //     System.out.println("Product deleted");
    //   } else {
    //     System.err.println("Product not found");
    //   }
    // } catch (SQLException e) {
    //   System.err.println("Error returned: " + e);
    // }
  }
}
