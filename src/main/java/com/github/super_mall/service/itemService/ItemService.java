package com.github.super_mall.service.itemService;

import com.github.super_mall.dto.itemDto.ItemAdditionalDto;
import com.github.super_mall.repository.categoryRepository.CategoryRepository;
import com.github.super_mall.repository.itemRepository.ItemRepository;
import org.springframework.stereotype.Service;
import com.github.super_mall.entity.itemEntity.Item;
import com.github.super_mall.entity.categoryEntity.Category;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    public List<Item> findByNameContaining(String nameKeyword) {
        return itemRepository.findItemByNameContaining(nameKeyword);
    }

    public Item addItem(ItemAdditionalDto addItem) {
        Category category = categoryRepository.findCategoryByCategory(addItem.getCategory())
                .orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));

        Item item = Item.toEntity(addItem, category);
        return itemRepository.save(item);
    }
}
