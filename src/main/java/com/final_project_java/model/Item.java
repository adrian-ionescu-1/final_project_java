package com.final_project_java.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "item_name", nullable = false)
    private String name;
    @Column(name = "item_description", nullable = false)
    private String description;
    @Column(name = "item_price", nullable = false)
    private double price;
    @Column(name = "item_image")
    private String image;
    @Column(name = "item_category")
    private String category;

    @ManyToMany(mappedBy = "itemList", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("itemList")
    private List<Order> orderList;
}
