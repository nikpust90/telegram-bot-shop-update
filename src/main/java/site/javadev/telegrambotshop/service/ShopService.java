package site.javadev.telegrambotshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.javadev.telegrambotshop.model.Product;
import site.javadev.telegrambotshop.model.Shop;
import site.javadev.telegrambotshop.repositories.ProductRepository;
import site.javadev.telegrambotshop.repositories.ShopRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<String> getShopNames() {
        return getShops()
                .stream()
                .map(Shop::getName)
                .toList();
    }

    @Transactional(readOnly = true)
    public Shop getShop(String shopName) {
        return shopRepository.findByName(shopName)
                .orElseThrow(() -> new RuntimeException("Магазин с названием '%s' не найден".formatted(shopName)));
    }

    @Transactional(readOnly = true)
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Product getProduct(String shopName) {
        Shop shop = getShop(shopName);
        return shop.getProducts().stream()
                .filter(product -> product.getName().equals(shopName))
                .findFirst().orElse(null);
    }
}
