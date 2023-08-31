package com.bienvan.store.model;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "color")
    private List<Product> products;
}
