package com.final_project_java.repository;

import com.final_project_java.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> searchItemsByName(String name);

    List<Item> searchItemsByCategory(String category);
}
