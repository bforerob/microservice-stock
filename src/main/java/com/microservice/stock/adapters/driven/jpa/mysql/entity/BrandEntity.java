package com.microservice.stock.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "brand")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "brand")
    private List<ArticleEntity> articles;

    public BrandEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
