package site.javadev.telegrambotshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@ToString(exclude = "shop") // Исключаем поле shop из toString, если оно может создавать циклические ссылки
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private Integer count;

    @Column(name = "articul")
    private String articul;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    private Shop shop;
}