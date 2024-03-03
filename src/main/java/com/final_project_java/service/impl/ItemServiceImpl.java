package com.final_project_java.service.impl;


import com.final_project_java.model.Item;
import com.final_project_java.repository.ItemRepository;
import com.final_project_java.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> readAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItemsByName(String name) {
        return itemRepository.searchItemsByName(name);
    }

    @Override
    public List<Item> getAllItemByCategory(String category) {
        return itemRepository.searchItemsByCategory(category);
    }
}
