package com.final_project_java.controller;

import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.Item;
import com.final_project_java.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // GET endpoint -> http://localhost:8080/api/items
    @GetMapping("/getAllItems")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> itemsList = itemService.readAllItems();
        if (itemsList.isEmpty()) {
            throw new ResourceNotFoundException("No items found in DB");
        }
        return new ResponseEntity<>(itemsList, HttpStatus.OK);
    }

    @GetMapping("/itemsById/{id}")
    public ResponseEntity<Optional<Item>> getItemById(@PathVariable Long id) {
        Optional<Item> itemById = itemService.getItemById(id);
        itemById.orElseThrow(() -> new ResourceNotFoundException("Item with id: " + id + " doesn't exist in DB"));
        return new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
    }

    @GetMapping("/itemsByName/{name}")
    public ResponseEntity<List<Item>> getAllItemsByName(@PathVariable String name) {
        List<Item> items = itemService.getAllItemsByName(name);
        if (items.isEmpty()) {
            throw new ResourceNotFoundException("No items found width: " + name + " in DB");
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/itemsByCategory/{category}")
    public ResponseEntity<List<Item>> getAllItemsByCategory(@PathVariable String category) {
        List<Item> itemsByCategory = itemService.getAllItemByCategory(category);
        if (itemsByCategory.isEmpty()) {
            throw new ResourceNotFoundException("No items found width: " + category + " in DB");
        }
        return new ResponseEntity<>(itemsByCategory, HttpStatus.OK);
    }

    @PostMapping("/addNewItem")
    public ResponseEntity<Item> saveItem(@RequestBody Item item) {
        Item newItem = itemService.saveItem(item);
        return new ResponseEntity<>(newItem, HttpStatus.OK);
    }

    @PutMapping("/updateItem")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        if (item.getId() == null) {
            throw new ResourceNotFoundException("Item id is not valid");
        }
        Optional<Item> itemOptional = itemService.getItemById(item.getId());
        itemOptional.orElseThrow(() ->
                new ResourceNotFoundException("Item with id: " + item.getId() + " doesn't exist in DB"));
        return new ResponseEntity<>(itemService.updateItem(item), HttpStatus.OK);
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        Optional<Item> itemOptional = itemService.getItemById(id);
        itemOptional.orElseThrow(() -> new ResourceNotFoundException("Item with id: " + id + " doesn't exist in DB"));
        itemService.deleteItemById(id);
        return new ResponseEntity<>("Item with id: " + id + " delete successfully", HttpStatus.OK);
    }
}
