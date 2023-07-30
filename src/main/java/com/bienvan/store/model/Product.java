package com.bienvan.store.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    private String image;

    @Min(value = 0, message = "Số lượng phải lớn hơn 0")
    private int quantity;

    @Min(value = 1, message = "Giá phải lớn hơn hoặc bằng 1")
    private double price;

    // Ví dụ khóa ngoại có quan hệ nhiều-1 (nhiều Product thuộc về một Category)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new HashSet<>();

}
