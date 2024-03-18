package com.final_project_java.controller;

import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.Item;
import com.final_project_java.service.ItemService;
import com.final_project_java.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/items") // http://localhost:8080/api/items
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllItems() {
        List<Item> itemsList = itemService.readAllItems();
        if (itemsList.isEmpty()) {
            throw new ResourceNotFoundException("No items found in DB");
        }

        return ResponseEntity.ok(ApiResponse.success("All items list", itemsList));
    }

    @GetMapping("/itemsById/{id}")
    public ResponseEntity<ApiResponse> getItemById(@PathVariable Long id) {
        Optional<Item> itemById = itemService.getItemById(id);
        itemById.orElseThrow(() -> new ResourceNotFoundException("Item with id: " + id + " doesn't exist in DB"));

        return ResponseEntity.ok(ApiResponse.success("Item by id", itemById.get()));
    }

    @GetMapping("/itemsByName/{name}")
    public ResponseEntity<ApiResponse> getAllItemsByName(@PathVariable String name) {
        List<Item> items = itemService.getAllItemsByName(name);
        if (items.isEmpty()) {
            throw new ResourceNotFoundException("No items found width: " + name + " in DB");
        }

        return ResponseEntity.ok(ApiResponse.success("Item by name", items));
    }

    @GetMapping("/itemsByCategory/{category}")
    public ResponseEntity<ApiResponse> getAllItemsByCategory(@PathVariable String category) {
        List<Item> itemsByCategory = itemService.getAllItemByCategory(category);
        if (itemsByCategory.isEmpty()) {
            throw new ResourceNotFoundException("No items found width: " + category + " in DB");
        }

        return ResponseEntity.ok(ApiResponse.success("Items by category", itemsByCategory));
    }

    @PostMapping("/addNewItem")
    public ResponseEntity<ApiResponse> saveItem(@RequestBody Item item) {
        Item newItem = itemService.saveItem(item);

        return ResponseEntity.ok(ApiResponse.success("Add new item", newItem));
    }

    @PutMapping("/updateItem")
    public ResponseEntity<ApiResponse> updateItem(@RequestBody Item item) {
        if (item.getId() == null) {
            throw new ResourceNotFoundException("Item id is not valid");
        }
        Optional<Item> itemOptional = itemService.getItemById(item.getId());
        itemOptional.orElseThrow(() ->
                new ResourceNotFoundException("Item with id: " + item.getId() + " doesn't exist in DB"));

        return ResponseEntity.ok(ApiResponse.success("Update item", itemService.updateItem(item)));
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long id) {
        Optional<Item> itemOptional = itemService.getItemById(id);
        itemOptional.orElseThrow(() -> new ResourceNotFoundException("Item with id: " + id + " doesn't exist in DB"));
        itemService.deleteItemById(id);

        return ResponseEntity.ok(ApiResponse.success("Item with id: " + id + " delete successfully", null));
    }
}
