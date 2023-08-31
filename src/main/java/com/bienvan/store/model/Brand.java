package com.bienvan.store.model;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
