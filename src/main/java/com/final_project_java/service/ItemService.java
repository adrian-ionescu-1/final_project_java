package com.final_project_java.service;

import com.final_project_java.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> readAllItems();

    Optional<Item> getItemById(Long id);

    Item saveItem(Item item);

    void deleteItemById(Long id);

    Item updateItem(Item item);

    List<Item> getAllItemsByName(String name);

    List<Item> getAllItemByCategory(String category);
}
