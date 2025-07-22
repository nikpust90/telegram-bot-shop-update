package site.javadev.telegrambotshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import site.javadev.telegrambotshop.model.Shop;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByName(String name);
}
