package site.javadev.telegrambotshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import site.javadev.telegrambotshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
