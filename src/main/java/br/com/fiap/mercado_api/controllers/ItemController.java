package br.com.fiap.mercado_api.controllers;

import br.com.fiap.mercado_api.models.EItemRarity;
import br.com.fiap.mercado_api.models.EItemType;
import br.com.fiap.mercado_api.models.Item;
import br.com.fiap.mercado_api.repositories.ItemRepository;
import br.com.fiap.mercado_api.specification.ItemSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/item")
@Slf4j
public class ItemController {

    public record ItemFilter(
            String nome,
            EItemType tipo,
            EItemRarity raridade,
            BigDecimal precoMin,
            BigDecimal precoMax
    ) {}

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    @Cacheable("item")
    @Operation(description = "Get all items", tags = "item", summary = "Item's list")
    public Page<Item> index(ItemFilter filter, @PageableDefault(size = 20) Pageable pageable) {
        Specification<Item> specification = ItemSpecification.withFilters(filter);
        return itemRepository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "item", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<Item> createItem(@RequestBody @Valid Item item) {
        itemRepository.save(item);
        return ResponseEntity.status(201).body(item);
    }

    @GetMapping("{id}")
    public Item getById(@PathVariable Long id) {
        return getItem(id);
    }

    @PutMapping("{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody @Valid Item updatedItem) {
        Item existingItem = getItem(id);

        existingItem.setNome(updatedItem.getNome());
        existingItem.setType(updatedItem.getType());
        existingItem.setRarity(updatedItem.getRarity());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setOwner(updatedItem.getOwner());

        return itemRepository.save(existingItem);
    }

    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.delete(getItem(id));
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
    }
}
